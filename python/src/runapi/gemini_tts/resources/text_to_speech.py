"""Gemini TTS text-to-speech resource."""

from __future__ import annotations

from typing import Any, Optional

from runapi.core import Resource, RequestOptions

from ..contract_gen import CONTRACT
from ..types import AudioTaskResponse, CompletedAudioTaskResponse


class TextToSpeech(Resource):
    """Generate multi-speaker speech from ordered dialogue turns."""

    ENDPOINT = "/api/v1/gemini_tts/text_to_speech"
    RESPONSE_CLASS = AudioTaskResponse
    COMPLETED_RESPONSE_CLASS = CompletedAudioTaskResponse

    def run(self, options: Optional[RequestOptions] = None, **params: Any) -> Any:
        task = self.create(options=options, **params)
        return self._poll_until_complete(lambda: self.get(task.id, options=options))

    def create(self, options: Optional[RequestOptions] = None, **params: Any) -> Any:
        compacted = self._compact_params(params)
        self._validate_contract(CONTRACT["text-to-speech"], compacted)
        return self._request("post", self.ENDPOINT, body=compacted, options=options)

    def get(self, id: str, options: Optional[RequestOptions] = None) -> Any:
        return self._request("get", f"{self.ENDPOINT}/{id}", options=options)
