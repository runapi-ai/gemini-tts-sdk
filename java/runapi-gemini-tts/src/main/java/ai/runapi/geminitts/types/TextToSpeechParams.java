package ai.runapi.geminitts.types;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Parameters for text to speech operations. */
public final class TextToSpeechParams {
  private final String model;
  private final Double temperature;
  private final String scene;
  private final String sampleContext;
  private final List<Speaker> speakers;
  private final List<DialogueTurn> dialogueTurns;
  private final String callbackUrl;

  private TextToSpeechParams(Builder builder) {
    this.model = GeminittsParamUtils.requireNonBlankTrim(builder.model, "model");
    this.temperature = builder.temperature;
    this.scene = builder.scene;
    this.sampleContext = builder.sampleContext;
    this.speakers = GeminittsParamUtils.requiredList(builder.speakers, "speakers");
    this.dialogueTurns = GeminittsParamUtils.requiredList(builder.dialogueTurns, "dialogueTurns");
    this.callbackUrl = builder.callbackUrl;
  }

  /** Creates a new TextToSpeechParams builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Returns the RunAPI action key for this request. */
  public String action() {
    return "gemini-tts/text-to-speech";
  }

  /** Converts these parameters to the JSON request body shape. */
  public Map<String, Object> toMap() {
    Map<String, Object> raw = new LinkedHashMap<String, Object>();
    raw.put("model", GeminittsParamUtils.wireValue(model));
    raw.put("temperature", GeminittsParamUtils.wireValue(temperature));
    raw.put("scene", GeminittsParamUtils.wireValue(scene));
    raw.put("sample_context", GeminittsParamUtils.wireValue(sampleContext));
    raw.put("speakers", speakersToMaps(speakers));
    raw.put("dialogue_turns", dialogueTurnsToMaps(dialogueTurns));
    raw.put("callback_url", GeminittsParamUtils.wireValue(callbackUrl));
    return GeminittsParamUtils.compact(raw);
  }

  private static List<Map<String, Object>> speakersToMaps(List<Speaker> values) {
    if (values == null) {
      return null;
    }
    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    for (Speaker item : values) {
      result.add(item.toMap());
    }
    return java.util.Collections.unmodifiableList(result);
  }

  private static List<Map<String, Object>> dialogueTurnsToMaps(List<DialogueTurn> values) {
    if (values == null) {
      return null;
    }
    List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
    for (DialogueTurn item : values) {
      result.add(item.toMap());
    }
    return java.util.Collections.unmodifiableList(result);
  }

  /** Builder for {@link TextToSpeechParams}. */
  public static final class Builder {
    private String model;
    private Double temperature;
    private String scene;
    private String sampleContext;
    private List<Speaker> speakers;
    private List<DialogueTurn> dialogueTurns;
    private String callbackUrl;

    private Builder() {}

    /** Sets the model slug using a typed model value. */
    public Builder model(TextToSpeechModel value) {
      this.model = java.util.Objects.requireNonNull(value, "model").value();
      return this;
    }

    /** Sets the model slug using a string value. */
    public Builder model(String value) {
      this.model = GeminittsParamUtils.requireNonBlankTrim(value, "model");
      return this;
    }


    /** Sets the temperature. */
    public Builder temperature(double value) {
      this.temperature = value;
      return this;
    }

    /** Sets the scene. */
    public Builder scene(String value) {
      this.scene = GeminittsParamUtils.requireNonBlank(value, "scene");
      return this;
    }

    /** Sets the sample context. */
    public Builder sampleContext(String value) {
      this.sampleContext = GeminittsParamUtils.requireNonBlank(value, "sampleContext");
      return this;
    }

    /** Sets the speakers. */
    public Builder speakers(List<Speaker> value) {
      this.speakers = value;
      return this;
    }

    /** Sets the dialogue turns. */
    public Builder dialogueTurns(List<DialogueTurn> value) {
      this.dialogueTurns = value;
      return this;
    }

    /** Sets the webhook URL for task completion notifications. */
    public Builder callbackUrl(String value) {
      this.callbackUrl = GeminittsParamUtils.requireNonBlank(value, "callbackUrl");
      return this;
    }

    /** Builds immutable text to speech parameters. */
    public TextToSpeechParams build() {
      return new TextToSpeechParams(this);
    }
  }
}
