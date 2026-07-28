import type { AsyncTaskStatus, TaskBillingResponse, TaskResponse } from '@runapi.ai/core';

/** Gemini TTS model exposed by RunAPI. */
export type GeminiTtsModel = 'gemini-2.5-pro-tts' | 'gemini-3.1-flash-tts';

export type GeminiTtsVoiceName =
  | 'Achernar' | 'Achird' | 'Algenib' | 'Algieba' | 'Alnilam' | 'Aoede'
  | 'Autonoe' | 'Callirrhoe' | 'Charon' | 'Despina' | 'Enceladus' | 'Erinome'
  | 'Fenrir' | 'Gacrux' | 'Iapetus' | 'Kore' | 'Laomedeia' | 'Leda' | 'Orus'
  | 'Puck' | 'Pulcherrima' | 'Rasalgethi' | 'Sadachbia' | 'Sadaltager'
  | 'Schedar' | 'Sulafat' | 'Umbriel' | 'Vindemiatrix' | 'Zephyr' | 'Zubenelgenubi';

export type GeminiTtsAccent =
  | 'Neutral' | 'American (Gen)' | 'American (Valley)' | 'American (South)'
  | 'British (RP)' | 'British (Brixton)' | 'Transatlantic' | 'Australian';

export type GeminiTtsStyle =
  | 'Vocal Smile' | 'Newscaster' | 'Whisper' | 'Empathetic' | 'Promo/Hype' | 'Deadpan';

export type GeminiTtsPace = 'Natural' | 'Rapid Fire' | 'The Drift' | 'Staccato';

/** Configuration for one dialogue speaker. */
export interface Speaker {
  /** Stable identifier in `Speaker N` format. */
  speaker_id: string;
  voice_name: GeminiTtsVoiceName;
  /** Optional description of the desired voice character. */
  audio_profile?: string;
  accent: GeminiTtsAccent;
  style: GeminiTtsStyle;
  pace: GeminiTtsPace;
}

/** One spoken turn, emitted in array order. */
export interface DialogueTurn {
  /** Must match a configured speaker's `speaker_id`. */
  speaker_id: string;
  /** Spoken text, up to 10,000 characters. */
  text: string;
}

export interface TextToSpeechParams {
  model: GeminiTtsModel;
  /** Sampling temperature from 0 to 2; defaults to 1. */
  temperature?: number;
  scene?: string;
  sample_context?: string;
  speakers: Speaker[];
  dialogue_turns: DialogueTurn[];
  callback_url?: string;
}

export interface TaskCreateResponse extends TaskBillingResponse {
  id: string;
  status?: AsyncTaskStatus;
}

export interface AudioFile {
  url: string;
}

export interface AudioTaskResponse extends TaskResponse {
  id: string;
  status: AsyncTaskStatus;
  audios?: AudioFile[];
  error?: string;
  [key: string]: unknown;
}

export type CompletedAudioTaskResponse = AudioTaskResponse & {
  status: 'completed';
  audios: AudioFile[];
};
