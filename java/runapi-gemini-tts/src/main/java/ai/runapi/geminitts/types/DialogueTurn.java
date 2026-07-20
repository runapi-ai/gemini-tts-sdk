package ai.runapi.geminitts.types;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Nested request item for typed parameter builders. */
public final class DialogueTurn {
  private final String speakerId;
  private final String text;

  private DialogueTurn(Builder builder) {
    this.speakerId = GeminittsParamUtils.requireNonBlank(builder.speakerId, "speakerId");
    this.text = GeminittsParamUtils.requireNonBlank(builder.text, "text");
  }

  /** Creates a new DialogueTurn builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Returns the speaker ID. */
  public String getSpeakerId() {
    return speakerId;
  }

  /** Returns the line text. */
  public String getText() {
    return text;
  }

  Map<String, Object> toMap() {
    Map<String, Object> raw = new LinkedHashMap<String, Object>();
    raw.put("speaker_id", GeminittsParamUtils.wireValue(speakerId));
    raw.put("text", GeminittsParamUtils.wireValue(text));
    return GeminittsParamUtils.compact(raw);
  }

  /** Builder for {@link DialogueTurn}. */
  public static final class Builder {
    private String speakerId;
    private String text;

    private Builder() {}

    /** Sets the speaker ID. */
    public Builder speakerId(String value) {
      this.speakerId = GeminittsParamUtils.requireNonBlank(value, "speakerId");
      return this;
    }

    /** Sets the line text. */
    public Builder text(String value) {
      this.text = GeminittsParamUtils.requireNonBlank(value, "text");
      return this;
    }

    /** Builds an immutable DialogueTurn. */
    public DialogueTurn build() {
      return new DialogueTurn(this);
    }
  }
}
