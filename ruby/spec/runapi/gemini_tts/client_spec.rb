# frozen_string_literal: true

require "spec_helper"

RSpec.describe RunApi::GeminiTts::Client do
  before { allow(ConnectionPool).to receive(:new).and_return(instance_double(ConnectionPool)) }
  after { RunApi.api_key = nil }

  it "accepts api_key and exposes text_to_speech" do
    client = described_class.new(api_key: "test-key")
    expect(client.text_to_speech).to be_a(RunApi::GeminiTts::Resources::TextToSpeech)
  end

  it "raises without an api key" do
    expect { described_class.new }.to raise_error(RunApi::Core::AuthenticationError, /API key is required/)
  end
end
