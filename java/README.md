# Gemini TTS Java SDK for RunAPI

The Java module provides immutable clients, typed builders, polling helpers, file helpers, and consistent RunAPI errors.

```kotlin
implementation("ai.runapi:runapi-gemini-tts:0.1.0")
```

```java
GeminiTtsClient client = GeminiTtsClient.builder()
    .apiKey(System.getenv("RUNAPI_API_KEY"))
    .build();

CompletedTextToSpeechResponse result = client.textToSpeech().run(
    TextToSpeechParams.builder()
        .model(TextToSpeechModel.GEMINI_2_5_PRO_TTS)
        .speakers(speakers)
        .dialogueTurns(dialogueTurns)
        .build()
);
```

See the [model page](https://runapi.ai/models/gemini-tts) and [API reference](https://runapi.ai/docs/api/gemini-tts/text-to-speech). Licensed under Apache-2.0.
