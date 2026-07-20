# frozen_string_literal: true

require "spec_helper"

RSpec.describe RunApi::GeminiTts::Resources::TextToSpeech do
  let(:http) { instance_double(RunApi::Core::HttpClient) }
  let(:resource) { described_class.new(http) }
  let(:endpoint) { "/api/v1/gemini_tts/text_to_speech" }
  let(:params) do
    {
      model: "gemini-2.5-pro-tts",
      temperature: 0.8,
      speakers: [{
        speaker_id: "Speaker 1", voice_name: "Fenrir", accent: "British (RP)",
        style: "Deadpan", pace: "Natural"
      }],
      dialogue_turns: [{speaker_id: "Speaker 1", text: "Welcome."}]
    }
  end

  it "POSTs the flat nested request" do
    expect(http).to receive(:request).with(:post, endpoint, body: params).and_return("id" => "task-1")
    expect(resource.create(**params).id).to eq("task-1")
  end

  it "GETs and decodes audio results" do
    expect(http).to receive(:request).with(:get, "#{endpoint}/task-1").and_return(
      "id" => "task-1", "status" => "completed",
      "audios" => [{"url" => "https://tempfile.runapi.ai/dialogue.mp3"}]
    )
    result = resource.get("task-1")
    expect(result.audios.first.url).to eq("https://tempfile.runapi.ai/dialogue.mp3")
  end

  it "requires speakers" do
    expect { resource.create(**params.except(:speakers)) }
      .to raise_error(RunApi::Core::ValidationError, /speakers is required/)
  end
end
