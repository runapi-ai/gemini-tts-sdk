# Gemini TTS Ruby SDK for RunAPI

Install `runapi-gemini-tts` for multi-speaker speech generation and async task polling.

```bash
gem install runapi-gemini-tts
```

```ruby
require "runapi/gemini_tts"

client = RunApi::GeminiTts::Client.new
result = client.text_to_speech.run(
  model: "gemini-2.5-pro-tts",
  speakers: [{speaker_id: "Speaker 1", voice_name: "Fenrir", accent: "British (RP)", style: "Deadpan", pace: "Natural"}],
  dialogue_turns: [{speaker_id: "Speaker 1", text: "Welcome."}]
)
```

See the [model page](https://runapi.ai/models/gemini-tts) and [API reference](https://runapi.ai/docs/api/gemini-tts/text-to-speech). Licensed under Apache-2.0.
