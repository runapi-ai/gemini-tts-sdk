package geminitts

import (
	"context"

	"github.com/runapi-ai/core-sdk/go/base"
	"github.com/runapi-ai/core-sdk/go/core"
	"github.com/runapi-ai/core-sdk/go/option"
)

const textToSpeechPath = "/api/v1/gemini_tts/text_to_speech"

// Client is the Gemini TTS multi-speaker speech client.
type Client struct {
	base.Base
	TextToSpeech *TextToSpeech
}

func NewClient(opts ...option.ClientOption) (*Client, error) {
	resolved, err := option.ResolveClientOptions(opts...)
	if err != nil {
		return nil, err
	}
	httpClient, err := core.NewHTTPClient(resolved)
	if err != nil {
		return nil, err
	}
	return NewClientWithHTTP(httpClient), nil
}

func NewClientWithHTTP(httpClient core.HTTPClient) *Client {
	return &Client{Base: base.New(httpClient), TextToSpeech: &TextToSpeech{http: httpClient}}
}

// TextToSpeech generates multi-speaker speech from ordered dialogue turns.
type TextToSpeech struct{ http core.HTTPClient }

func (r *TextToSpeech) Create(ctx context.Context, params TextToSpeechParams, opts ...option.RequestOption) (*core.TaskCreateResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	body := core.CompactParams(params)
	if err := core.ValidateParams(contractSchema["text-to-speech"], body); err != nil {
		return nil, err
	}
	return core.PostJSON[core.TaskCreateResponse](ctx, r.http, textToSpeechPath, body, requestOptions)
}

func (r *TextToSpeech) Get(ctx context.Context, id string, opts ...option.RequestOption) (*AudioTaskResponse, error) {
	requestOptions, _ := option.ResolveRequestOptions(opts...)
	return core.GetJSON[AudioTaskResponse](ctx, r.http, core.ResourcePath(textToSpeechPath, id), requestOptions)
}

func (r *TextToSpeech) Run(ctx context.Context, params TextToSpeechParams, opts ...option.RequestOption) (*AudioTaskResponse, error) {
	_, pollingOptions := option.ResolveRequestOptions(opts...)
	return core.RunAsync(ctx,
		func(ctx context.Context) (*core.TaskCreateResponse, error) { return r.Create(ctx, params, opts...) },
		func(ctx context.Context, id string) (*AudioTaskResponse, error) { return r.Get(ctx, id, opts...) },
		pollingOptions,
	)
}
