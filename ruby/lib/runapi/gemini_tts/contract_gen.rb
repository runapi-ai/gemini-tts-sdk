# frozen_string_literal: true

module RunApi
  module GeminiTts
    CONTRACT = {
      "text-to-speech" => {
        "models" => ["gemini-2.5-pro-tts", "gemini-3.1-flash-tts"],
        "fields_by_model" => {
          "gemini-2.5-pro-tts" => {
            "dialogue_turns" => {
              "required" => true,
              "min_items" => 1
            },
            "model" => {
              "required" => true
            },
            "speakers" => {
              "required" => true,
              "min_items" => 1
            },
            "temperature" => {
              "min" => 0,
              "max" => 2
            }
          },
          "gemini-3.1-flash-tts" => {
            "dialogue_turns" => {
              "required" => true,
              "min_items" => 1
            },
            "model" => {
              "required" => true
            },
            "speakers" => {
              "required" => true,
              "min_items" => 1
            },
            "temperature" => {
              "min" => 0,
              "max" => 2
            }
          }
        }
      }
    }.freeze
  end
end
