package geminitts

import (
	"context"
	"encoding/json"
	"testing"

	"github.com/runapi-ai/core-sdk/go/core"
)

type stubHTTPClient struct {
	method   string
	path     string
	body     any
	response json.RawMessage
}

func (s *stubHTTPClient) Request(_ context.Context, method, path string, opts *core.HTTPRequestOptions) (json.RawMessage, error) {
	s.method = method
	s.path = path
	if opts != nil {
		s.body = opts.Body
	}
	return s.response, nil
}

func TestTextToSpeechCreate(t *testing.T) {
	stub := &stubHTTPClient{response: json.RawMessage(`{"id":"task_123","status":"processing"}`)}
	client := NewClientWithHTTP(stub)
	temperature := 0.8
	resp, err := client.TextToSpeech.Create(context.Background(), TextToSpeechParams{
		Model:       ModelGemini25ProTTS,
		Temperature: &temperature,
		Speakers: []Speaker{{
			SpeakerID: "Speaker 1", VoiceName: "Fenrir", Accent: "British (RP)", Style: "Deadpan", Pace: "Natural",
		}},
		DialogueTurns: []DialogueTurn{{SpeakerID: "Speaker 1", Text: "Welcome."}},
	})
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "POST" || stub.path != textToSpeechPath {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
	body := stub.body.(map[string]any)
	if body["model"] != "gemini-2.5-pro-tts" || body["temperature"] != 0.8 {
		t.Fatalf("unexpected body: %v", body)
	}
	speakers := body["speakers"].([]any)
	turns := body["dialogue_turns"].([]any)
	if len(speakers) != 1 || len(turns) != 1 ||
		speakers[0].(map[string]any)["speaker_id"] != "Speaker 1" ||
		turns[0].(map[string]any)["text"] != "Welcome." {
		t.Fatalf("nested arrays missing from body: %v", body)
	}
	if resp.ID != "task_123" {
		t.Fatalf("unexpected task ID: %s", resp.ID)
	}
}

func TestTextToSpeechGet(t *testing.T) {
	stub := &stubHTTPClient{response: json.RawMessage(`{"id":"task_456","status":"completed","audios":[{"url":"https://tempfile.runapi.ai/dialogue.mp3"}]}`)}
	client := NewClientWithHTTP(stub)
	resp, err := client.TextToSpeech.Get(context.Background(), "task_456")
	if err != nil {
		t.Fatal(err)
	}
	if stub.method != "GET" || stub.path != textToSpeechPath+"/task_456" {
		t.Fatalf("unexpected request: %s %s", stub.method, stub.path)
	}
	if len(resp.Audios) != 1 || resp.Audios[0].URL != "https://tempfile.runapi.ai/dialogue.mp3" {
		t.Fatalf("unexpected response: %+v", resp)
	}
}
