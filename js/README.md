# Gemini TTS JavaScript SDK for RunAPI

Install `@runapi.ai/gemini-tts` to generate multi-speaker speech with typed JavaScript and TypeScript requests.

```bash
npm install @runapi.ai/gemini-tts
```

```typescript
import { GeminiTtsClient } from '@runapi.ai/gemini-tts';

const client = new GeminiTtsClient();
const result = await client.textToSpeech.run({
  model: 'gemini-3.1-flash-tts',
  speakers: [{ speaker_id: 'Speaker 1', voice_name: 'Puck', accent: 'Neutral', style: 'Newscaster', pace: 'Natural' }],
  dialogue_turns: [{ speaker_id: 'Speaker 1', text: 'Today in brief.' }],
});
```

See the [model page](https://runapi.ai/models/gemini-tts) and [API reference](https://runapi.ai/docs/api/gemini-tts/text-to-speech). Licensed under Apache-2.0.
