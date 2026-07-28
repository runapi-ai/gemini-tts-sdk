# Gemini TTS Go SDK for RunAPI

Install the model-specific Go module for typed multi-speaker speech generation.

```bash
go get github.com/runapi-ai/gemini-tts-sdk/go@latest
```

```go
client, err := geminitts.NewClient()
result, err := client.TextToSpeech.Run(ctx, geminitts.TextToSpeechParams{
  Model: geminitts.ModelGemini25ProTTS,
  Speakers: []geminitts.Speaker{
    {SpeakerID: "Speaker 1", VoiceName: "Fenrir", Accent: "British (RP)", Style: "Deadpan", Pace: "Natural"},
  },
  DialogueTurns: []geminitts.DialogueTurn{
    {SpeakerID: "Speaker 1", Text: "Welcome."},
  },
})
```

See the [model page](https://runapi.ai/models/gemini-tts) and [API reference](https://runapi.ai/docs/api/gemini-tts/text-to-speech). Licensed under Apache-2.0.
