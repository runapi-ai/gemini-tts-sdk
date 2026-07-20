"""Gemini TTS response models."""

from runapi.core import BaseModel, TaskResponse, optional, required


class Audio(BaseModel):
    url = required(str)


class AudioTaskResponse(TaskResponse):
    id = required(str)
    status = optional(str, enum=lambda: TaskResponse.Status.ALL)
    audios = optional([lambda: Audio])
    error = optional(str)


class CompletedAudioTaskResponse(AudioTaskResponse):
    audios = required([lambda: Audio])
