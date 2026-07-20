package ai.runapi.geminitts.types;

import com.fasterxml.jackson.annotation.JsonCreator;

/** Model slug for text to speech operations. */
public final class TextToSpeechModel extends GeminittsValue {
  /** gemini-2.5-pro-tts model slug. */
  public static final TextToSpeechModel GEMINI_2_5_PRO_TTS = new TextToSpeechModel("gemini-2.5-pro-tts");
  /** gemini-3.1-flash-tts model slug. */
  public static final TextToSpeechModel GEMINI_3_1_FLASH_TTS = new TextToSpeechModel("gemini-3.1-flash-tts");

  /** Creates a model value from a literal model slug. */
  @JsonCreator
  public TextToSpeechModel(String value) {
    super(value);
  }
}
