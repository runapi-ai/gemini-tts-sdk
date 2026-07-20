# Gemini TTS Python SDK for RunAPI

Install `runapi-gemini-tts` for keyword-based multi-speaker speech requests and normalized task responses.

```bash
pip install runapi-gemini-tts
```

```python
from runapi.gemini_tts import GeminiTtsClient

client = GeminiTtsClient()
result = client.text_to_speech.run(
    model="gemini-2.5-pro-tts",
    speakers=[{"speaker_id": "Speaker 1", "voice_name": "Fenrir", "accent": "British (RP)", "style": "Deadpan", "pace": "Natural"}],
    dialogue_turns=[{"speaker_id": "Speaker 1", "text": "Welcome."}],
)
```

See the [model page](https://runapi.ai/models/gemini-tts) and [API reference](https://runapi.ai/docs#gemini-tts). Licensed under Apache-2.0.
