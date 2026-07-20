import pytest

from runapi.core import config
from runapi.core.errors import AuthenticationError, ValidationError
from runapi.gemini_tts import GeminiTtsClient
from runapi.gemini_tts.resources.text_to_speech import TextToSpeech
from runapi.gemini_tts.types import CompletedAudioTaskResponse


class FakeHttp:
    def __init__(self, *responses):
        self._responses = list(responses)
        self.calls = []

    def request(self, method, path, body=None, options=None):
        self.calls.append((method, path, body))
        return self._responses.pop(0) if self._responses else {"id": "task_1", "status": "processing"}


@pytest.fixture(autouse=True)
def reset_config(monkeypatch):
    monkeypatch.delenv("RUNAPI_API_KEY", raising=False)
    monkeypatch.setattr(config, "api_key", None)
    yield


def valid_params():
    return {
        "model": "gemini-2.5-pro-tts",
        "temperature": 0.8,
        "speakers": [{
            "speaker_id": "Speaker 1",
            "voice_name": "Fenrir",
            "accent": "British (RP)",
            "style": "Deadpan",
            "pace": "Natural",
        }],
        "dialogue_turns": [{"speaker_id": "Speaker 1", "text": "Welcome."}],
    }


def test_raises_without_api_key():
    with pytest.raises(AuthenticationError, match="API key is required"):
        GeminiTtsClient()


def test_exposes_text_to_speech_resource():
    client = GeminiTtsClient(api_key="k", http_client=FakeHttp())
    assert isinstance(client.text_to_speech, TextToSpeech)


def test_create_posts_flat_nested_body():
    fake = FakeHttp({"id": "task_1", "status": "processing"})
    client = GeminiTtsClient(api_key="k", http_client=fake)
    result = client.text_to_speech.create(**valid_params())

    assert fake.calls == [("post", "/api/v1/gemini_tts/text_to_speech", valid_params())]
    assert result.id == "task_1"


def test_get_decodes_audio_results():
    fake = FakeHttp({
        "id": "task_1",
        "status": "completed",
        "audios": [{"url": "https://tempfile.runapi.ai/dialogue.mp3"}],
    })
    client = GeminiTtsClient(api_key="k", http_client=fake)
    result = client.text_to_speech.get("task_1")

    assert fake.calls == [("get", "/api/v1/gemini_tts/text_to_speech/task_1", None)]
    assert result.audios[0].url == "https://tempfile.runapi.ai/dialogue.mp3"


def test_run_returns_completed_response():
    fake = FakeHttp(
        {"id": "task_1", "status": "processing"},
        {"id": "task_1", "status": "completed", "audios": [{"url": "https://tempfile.runapi.ai/dialogue.mp3"}]},
    )
    client = GeminiTtsClient(api_key="k", http_client=fake)
    result = client.text_to_speech.run(**valid_params())

    assert isinstance(result, CompletedAudioTaskResponse)


def test_contract_requires_nested_collections():
    client = GeminiTtsClient(api_key="k", http_client=FakeHttp())
    params = valid_params()
    params.pop("speakers")

    with pytest.raises(ValidationError, match="speakers is required"):
        client.text_to_speech.create(**params)
