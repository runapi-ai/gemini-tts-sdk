# frozen_string_literal: true

Dir.chdir(__dir__) do

  Gem::Specification.new do |spec|
    spec.name = "runapi-gemini-tts"
    spec.version = "0.1.4"
    spec.metadata["runapi_slug"] = "gemini-tts"
    spec.authors = ["RunAPI"]
    spec.email = ["contact@runapi.ai"]

    spec.summary = "Gemini TTS Ruby SDK for RunAPI"
    spec.description = "Install `runapi-gemini-tts` for multi-speaker speech generation and async task polling."
    spec.homepage = "https://runapi.ai/models/gemini-tts"
    spec.license = "Apache-2.0"
    spec.required_ruby_version = ">= 3.1.0"
    spec.metadata["homepage_uri"] = "https://runapi.ai/models/gemini-tts"
    spec.metadata["documentation_uri"] = "https://github.com/runapi-ai/gemini-tts-sdk/blob/main/ruby/README.md"
    spec.metadata["source_code_uri"] = "https://github.com/runapi-ai/gemini-tts-sdk"
    spec.metadata["bug_tracker_uri"] = "https://github.com/runapi-ai/gemini-tts-sdk/issues"
    spec.metadata["changelog_uri"] = "https://github.com/runapi-ai/gemini-tts-sdk/blob/main/CHANGELOG.md"


    spec.files = Dir.glob("lib/**/*") + %w[LICENSE README.md]
    spec.extra_rdoc_files = ["README.md"]
        spec.require_paths = ["lib"]
    spec.add_dependency "runapi-core", "~> 0.4.0"
  end
end
