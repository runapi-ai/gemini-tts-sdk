<p align="center">
  <a href="https://runapi.ai"><img src="https://runapi.ai/icon.svg" height="56" alt="RunAPI"></a>
</p>

<h3 align="center">
  <a href="https://github.com/runapi-ai/gemini-tts-sdk">Gemini TTS API SDK for RunAPI</a>
</h3>

<p align="center">
  Gemini TTS API SDKs for JavaScript, Python, Ruby, Go, Java, and PHP on RunAPI, covering multi-speaker speech generation.
</p>

<div align="center">

[![npm](https://img.shields.io/npm/v/@runapi.ai/gemini-tts)](https://www.npmjs.com/package/@runapi.ai/gemini-tts)
[![PyPI](https://img.shields.io/pypi/v/runapi-gemini-tts)](https://pypi.org/project/runapi-gemini-tts/)
[![RubyGems](https://img.shields.io/gem/v/runapi-gemini-tts)](https://rubygems.org/gems/runapi-gemini-tts)
[![Go Reference](https://pkg.go.dev/badge/github.com/runapi-ai/gemini-tts-sdk/go.svg)](https://pkg.go.dev/github.com/runapi-ai/gemini-tts-sdk/go)
[![Maven Central](https://img.shields.io/maven-central/v/ai.runapi/runapi-gemini-tts)](https://central.sonatype.com/artifact/ai.runapi/runapi-gemini-tts)
[![License](https://img.shields.io/github/license/runapi-ai/gemini-tts-sdk)](https://github.com/runapi-ai/gemini-tts-sdk/blob/main/LICENSE)

</div>
<br/>

Gemini TTS on RunAPI turns ordered dialogue into speech with per-speaker voices, accents, styles, pacing, and optional voice profiles. The SDKs provide typed requests and a consistent create, get, and run lifecycle.

## Install

```bash
npm install @runapi.ai/gemini-tts
pip install runapi-gemini-tts
gem install runapi-gemini-tts
go get github.com/runapi-ai/gemini-tts-sdk/go@latest
```

Gradle:

```kotlin
implementation("ai.runapi:runapi-gemini-tts:0.1.1")
```

The PHP package is published from the split Composer repository as `runapi-ai/gemini-tts`; see https://github.com/runapi-ai/gemini-tts-php.

## JavaScript quick start

```typescript
import { GeminiTtsClient } from '@runapi.ai/gemini-tts';

const client = new GeminiTtsClient();
const result = await client.textToSpeech.run({
  model: 'gemini-2.5-pro-tts',
  speakers: [{
    speaker_id: 'Speaker 1',
    voice_name: 'Fenrir',
    accent: 'British (RP)',
    style: 'Deadpan',
    pace: 'Natural',
  }],
  dialogue_turns: [{ speaker_id: 'Speaker 1', text: 'Welcome.' }],
});

console.log(result.audios[0].url);
```

Use `create()` to submit without waiting, `get(id)` to fetch task status, or `run()` to submit and poll until completion.

## Links

- Model page: https://runapi.ai/models/gemini-tts
- Gemini 2.5 Pro TTS: https://runapi.ai/models/gemini-tts/gemini-2.5-pro-tts
- Gemini 3.1 Flash TTS: https://runapi.ai/models/gemini-tts/gemini-3.1-flash-tts
- API reference: https://runapi.ai/docs/api/gemini-tts/text-to-speech
- Provider comparison: https://runapi.ai/providers/google
- Full catalog: https://runapi.ai/models

RunAPI-generated file URLs are temporary. Store generated audio in your own durable storage.

Licensed under the Apache License, Version 2.0.
