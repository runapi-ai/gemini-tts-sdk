# frozen_string_literal: true

module RunApi
  module GeminiTts
    module Types
      class Audio < RunApi::Core::BaseModel
        required :url, String
      end

      class AudioTaskResponse < RunApi::Core::TaskResponse
        required :id, String
        optional :status, String, enum: -> { RunApi::Core::TaskResponse::Status::ALL }
        optional :audios, [-> { Audio }]
        optional :error, String
      end

      class CompletedAudioTaskResponse < AudioTaskResponse
        required :audios, [-> { Audio }]
      end
    end
  end
end
