package ai.runapi.geminitts.types;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Nested request item for typed parameter builders. */
public final class Speaker {
  private final String speakerId;
  private final String voiceName;
  private final String audioProfile;
  private final String accent;
  private final String style;
  private final String pace;

  private Speaker(Builder builder) {
    this.speakerId = GeminittsParamUtils.requireNonBlank(builder.speakerId, "speakerId");
    this.voiceName = GeminittsParamUtils.requireNonBlank(builder.voiceName, "voiceName");
    this.audioProfile = builder.audioProfile;
    this.accent = GeminittsParamUtils.requireNonBlank(builder.accent, "accent");
    this.style = GeminittsParamUtils.requireNonBlank(builder.style, "style");
    this.pace = GeminittsParamUtils.requireNonBlank(builder.pace, "pace");
  }

  /** Creates a new Speaker builder. */
  public static Builder builder() {
    return new Builder();
  }

  /** Returns the speaker ID. */
  public String getSpeakerId() {
    return speakerId;
  }

  /** Returns the voice name. */
  public String getVoiceName() {
    return voiceName;
  }

  /** Returns the audio profile. */
  public String getAudioProfile() {
    return audioProfile;
  }

  /** Returns the accent. */
  public String getAccent() {
    return accent;
  }

  /** Returns the style. */
  public String getStyle() {
    return style;
  }

  /** Returns the pace. */
  public String getPace() {
    return pace;
  }

  Map<String, Object> toMap() {
    Map<String, Object> raw = new LinkedHashMap<String, Object>();
    raw.put("speaker_id", GeminittsParamUtils.wireValue(speakerId));
    raw.put("voice_name", GeminittsParamUtils.wireValue(voiceName));
    raw.put("audio_profile", GeminittsParamUtils.wireValue(audioProfile));
    raw.put("accent", GeminittsParamUtils.wireValue(accent));
    raw.put("style", GeminittsParamUtils.wireValue(style));
    raw.put("pace", GeminittsParamUtils.wireValue(pace));
    return GeminittsParamUtils.compact(raw);
  }

  /** Builder for {@link Speaker}. */
  public static final class Builder {
    private String speakerId;
    private String voiceName;
    private String audioProfile;
    private String accent;
    private String style;
    private String pace;

    private Builder() {}

    /** Sets the speaker ID. */
    public Builder speakerId(String value) {
      this.speakerId = GeminittsParamUtils.requireNonBlank(value, "speakerId");
      return this;
    }

    /** Sets the voice name. */
    public Builder voiceName(String value) {
      this.voiceName = GeminittsParamUtils.requireNonBlank(value, "voiceName");
      return this;
    }

    /** Sets the audio profile. */
    public Builder audioProfile(String value) {
      this.audioProfile = GeminittsParamUtils.requireNonBlank(value, "audioProfile");
      return this;
    }

    /** Sets the accent. */
    public Builder accent(String value) {
      this.accent = GeminittsParamUtils.requireNonBlank(value, "accent");
      return this;
    }

    /** Sets the style. */
    public Builder style(String value) {
      this.style = GeminittsParamUtils.requireNonBlank(value, "style");
      return this;
    }

    /** Sets the pace. */
    public Builder pace(String value) {
      this.pace = GeminittsParamUtils.requireNonBlank(value, "pace");
      return this;
    }

    /** Builds an immutable Speaker. */
    public Speaker build() {
      return new Speaker(this);
    }
  }
}
