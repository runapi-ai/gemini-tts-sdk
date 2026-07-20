import type { ActionSchema, HttpClient, PollingOptions, RequestOptions } from '@runapi.ai/core';
import { compactParams, validateParams } from '@runapi.ai/core';
import { pollUntilComplete } from '@runapi.ai/core/internal';
import { contract } from '../contract_gen';
import type { AudioTaskResponse, CompletedAudioTaskResponse, TaskCreateResponse, TextToSpeechParams } from '../types';

const ENDPOINT = '/api/v1/gemini_tts/text_to_speech';

/** Multi-speaker text-to-speech generation. */
export class TextToSpeech {
  constructor(private readonly http: HttpClient) {}

  async run(params: TextToSpeechParams, options?: RequestOptions & PollingOptions): Promise<CompletedAudioTaskResponse> {
    const { id } = await this.create(params, options);
    const response = await pollUntilComplete<AudioTaskResponse>(() => this.get(id, options), {
      maxWaitMs: options?.maxWaitMs,
      pollIntervalMs: options?.pollIntervalMs,
    });
    return response as CompletedAudioTaskResponse;
  }

  async create(params: TextToSpeechParams, options?: RequestOptions): Promise<TaskCreateResponse> {
    const body = compactParams(params);
    validateParams(contract['text-to-speech'] as ActionSchema, body as Record<string, unknown>);
    return this.http.request<TaskCreateResponse>('POST', ENDPOINT, { body, ...options });
  }

  async get(id: string, options?: RequestOptions): Promise<AudioTaskResponse> {
    return this.http.request<AudioTaskResponse>('GET', `${ENDPOINT}/${id}`, options ?? {});
  }
}
