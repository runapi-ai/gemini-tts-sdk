import { BaseClient, type ClientOptions } from '@runapi.ai/core';
import { TextToSpeech } from './resources/text-to-speech';

/** Gemini TTS multi-speaker speech client. */
export class GeminiTtsClient extends BaseClient {
  public readonly textToSpeech: TextToSpeech;

  constructor(options: ClientOptions = {}) {
    super(options);
    this.textToSpeech = new TextToSpeech(this.http);
  }
}
