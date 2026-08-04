// Package geminitts provides the Gemini TTS multi-speaker speech API client.
package geminitts

import "github.com/runapi-ai/core-sdk/go/core"

// Model selects the Gemini TTS model.
type Model string

const (
	ModelGemini25ProTTS   Model = "gemini-2.5-pro-tts"
	ModelGemini31FlashTTS Model = "gemini-3.1-flash-tts"
)

// TaskStatus is the async task lifecycle state.
type TaskStatus string

// Speaker configures one dialogue voice.
type Speaker struct {
	SpeakerID    string `json:"speaker_id" help:"required; identifier in Speaker N format"`
	VoiceName    string `json:"voice_name" help:"required; Gemini TTS voice name"`
	AudioProfile string `json:"audio_profile,omitempty" help:"optional; desired voice character"`
	Accent       string `json:"accent,omitempty" help:"optional; supported accent"`
	Style        string `json:"style,omitempty" help:"optional; supported delivery style"`
	Pace         string `json:"pace,omitempty" help:"optional; supported delivery pace"`
}

// DialogueTurn is one spoken turn, emitted in array order.
type DialogueTurn struct {
	SpeakerID string `json:"speaker_id" help:"required; must match a configured speaker"`
	Text      string `json:"text" help:"required; max 10000 chars"`
}

// TextToSpeechParams configures multi-speaker speech generation.
type TextToSpeechParams struct {
	Model         Model          `json:"model" help:"required; model slug"`
	Temperature   *float64       `json:"temperature,omitempty" help:"optional; 0-2; default 1"`
	Scene         string         `json:"scene,omitempty" help:"optional; scene and recording environment"`
	SampleContext string         `json:"sample_context,omitempty" help:"optional; overall delivery context and tone"`
	Speakers      []Speaker      `json:"speakers" help:"required; non-empty speaker configurations"`
	DialogueTurns []DialogueTurn `json:"dialogue_turns" help:"required; non-empty ordered dialogue turns"`
	CallbackURL   string         `json:"callback_url,omitempty" help:"optional; HTTPS callback URL"`
}

// AudioFile holds a generated audio URL.
type AudioFile struct {
	URL string `json:"url"`
}

// AudioTaskResponse is the normalized task status and speech result.
type AudioTaskResponse struct {
	core.TaskBillingFacts
	ID     string      `json:"id"`
	Status TaskStatus  `json:"status"`
	Audios []AudioFile `json:"audios,omitempty"`
	Error  string      `json:"error,omitempty"`
}

func (r AudioTaskResponse) GetID() string     { return r.ID }
func (r AudioTaskResponse) GetStatus() string { return string(r.Status) }
func (r AudioTaskResponse) GetError() string  { return r.Error }
