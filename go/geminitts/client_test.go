package geminitts

import (
	"context"
	"encoding/json"
	"testing"

	"github.com/runapi-ai/core-sdk/go/core"
)

func TestAudioTaskResponseParsesBillingFacts(t *testing.T) {
	var response AudioTaskResponse
	err := json.Unmarshal([]byte(`{"id":"task_123","status":"completed","billing":{"reservation":{"amount_cents":10},"settlement":{"charged_amount_cents":9,"amount_micro_cents":950000},"refund":{"refunded_at":"2026-07-23T00:00:00.000000Z"}}}`), &response)
	if err != nil {
		t.Fatal(err)
	}
	if response.Billing == nil || response.Billing.Reservation == nil || response.Billing.Settlement == nil || response.Billing.Refund == nil {
		t.Fatalf("expected complete billing facts: %#v", response.Billing)
	}
}

func TestAudioTaskResponsePreservesLargeBillingAmounts(t *testing.T) {
	var response AudioTaskResponse
	err := json.Unmarshal([]byte(`{"billing":{"reservation":{"amount_cents":2147483648},"settlement":{"charged_amount_cents":2147483649,"amount_micro_cents":2147483650}}}`), &response)
	if err != nil {
		t.Fatal(err)
	}
	if response.Billing == nil || response.Billing.Reservation == nil || response.Billing.Settlement == nil {
		t.Fatalf("expected billing facts: %#v", response.Billing)
	}
	if response.Billing.Reservation.AmountCents != 2_147_483_648 ||
		response.Billing.Settlement.ChargedAmountCents != 2_147_483_649 ||
		response.Billing.Settlement.AmountMicroCents != 2_147_483_650 {
		t.Fatalf("large billing amounts were not preserved: %#v", response.Billing)
	}
}

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
			SpeakerID: "Speaker 1", VoiceName: "Fenrir",
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
	for _, key := range []string{"accent", "style", "pace"} {
		if _, ok := speakers[0].(map[string]any)[key]; ok {
			t.Fatalf("optional speaker field %q should be omitted: %v", key, body)
		}
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
