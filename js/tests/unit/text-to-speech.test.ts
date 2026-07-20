import { beforeEach, describe, expect, it, vi } from 'vitest';
import type { HttpClient } from '@runapi.ai/core';
import { TextToSpeech } from '../../src/resources/text-to-speech';
import type { AudioTaskResponse, TaskCreateResponse } from '../../src/types';

describe('TextToSpeech', () => {
  const mockHttp: HttpClient = { request: vi.fn() };

  beforeEach(() => vi.clearAllMocks());

  it('posts flat params with nested speakers and dialogue turns', async () => {
    const response: TaskCreateResponse = { id: 'task-123', status: 'processing' };
    vi.mocked(mockHttp.request).mockResolvedValueOnce(response);
    const resource = new TextToSpeech(mockHttp);

    const result = await resource.create({
      model: 'gemini-2.5-pro-tts',
      temperature: 0.8,
      speakers: [{
        speaker_id: 'Speaker 1', voice_name: 'Fenrir', accent: 'British (RP)',
        style: 'Deadpan', pace: 'Natural',
      }],
      dialogue_turns: [{ speaker_id: 'Speaker 1', text: 'Welcome.' }],
    });

    expect(mockHttp.request).toHaveBeenCalledWith('POST', '/api/v1/gemini_tts/text_to_speech', {
      body: {
        model: 'gemini-2.5-pro-tts',
        temperature: 0.8,
        speakers: [{
          speaker_id: 'Speaker 1', voice_name: 'Fenrir', accent: 'British (RP)',
          style: 'Deadpan', pace: 'Natural',
        }],
        dialogue_turns: [{ speaker_id: 'Speaker 1', text: 'Welcome.' }],
      },
    });
    expect(result).toEqual(response);
  });

  it('gets and decodes audio results', async () => {
    const response: AudioTaskResponse = {
      id: 'task-123', status: 'completed', audios: [{ url: 'https://tempfile.runapi.ai/dialogue.mp3' }],
    };
    vi.mocked(mockHttp.request).mockResolvedValueOnce(response);
    const resource = new TextToSpeech(mockHttp);

    await expect(resource.get('task-123')).resolves.toEqual(response);
    expect(mockHttp.request).toHaveBeenCalledWith(
      'GET', '/api/v1/gemini_tts/text_to_speech/task-123', {},
    );
  });
});
