"""Gemini TTS client for RunAPI."""

from runapi.core import (
    AuthenticationError,
    InsufficientCreditsError,
    NotFoundError,
    RateLimitError,
    TaskFailedError,
    TaskTimeoutError,
    ValidationError,
)

from .client import GeminiTtsClient

__all__ = [
    "GeminiTtsClient",
    "AuthenticationError",
    "RateLimitError",
    "InsufficientCreditsError",
    "NotFoundError",
    "ValidationError",
    "TaskFailedError",
    "TaskTimeoutError",
]
