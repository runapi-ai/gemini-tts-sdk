# frozen_string_literal: true

require "runapi/core"
require_relative "gemini_tts/types"
require_relative "gemini_tts/contract_gen"
require_relative "gemini_tts/resources/text_to_speech"
require_relative "gemini_tts/client"

module RunApi
  module GeminiTts
    AuthenticationError = RunApi::Core::AuthenticationError
    RateLimitError = RunApi::Core::RateLimitError
    InsufficientCreditsError = RunApi::Core::InsufficientCreditsError
    NotFoundError = RunApi::Core::NotFoundError
    ValidationError = RunApi::Core::ValidationError
    TaskFailedError = RunApi::Core::TaskFailedError
    TaskTimeoutError = RunApi::Core::TaskTimeoutError
  end
end
