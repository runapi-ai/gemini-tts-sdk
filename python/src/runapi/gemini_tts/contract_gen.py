CONTRACT = {
    "text-to-speech": {
        "models": ["gemini-2.5-pro-tts", "gemini-3.1-flash-tts"],
        "fields_by_model": {
            "gemini-2.5-pro-tts": {
                "dialogue_turns": {
                    "required": True,
                    "min_items": 1
                },
                "model": {
                    "required": True
                },
                "speakers": {
                    "required": True,
                    "min_items": 1
                },
                "temperature": {
                    "min": 0,
                    "max": 2
                }
            },
            "gemini-3.1-flash-tts": {
                "dialogue_turns": {
                    "required": True,
                    "min_items": 1
                },
                "model": {
                    "required": True
                },
                "speakers": {
                    "required": True,
                    "min_items": 1
                },
                "temperature": {
                    "min": 0,
                    "max": 2
                }
            }
        }
    }
}
