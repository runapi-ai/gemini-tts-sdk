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
    this.speakerId = builder.speakerId;
    this.voiceName = builder.voiceName;
    this.audioProfile = builder.audioProfile;
    this.accent = builder.accent;
    this.style = builder.style;
    this.pace = builder.pace;
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
      this.speakerId = value;
      return this;
    }

    /** Sets the voice name. */
    public Builder voiceName(String value) {
      this.voiceName = value;
      return this;
    }

    /** Sets the audio profile. */
    public Builder audioProfile(String value) {
      this.audioProfile = value;
      return this;
    }

    /** Sets the accent. */
    public Builder accent(String value) {
      this.accent = value;
      return this;
    }

    /** Sets the style. */
    public Builder style(String value) {
      this.style = value;
      return this;
    }

    /** Sets the pace. */
    public Builder pace(String value) {
      this.pace = value;
      return this;
    }

    /** Builds an immutable Speaker. */
    public Speaker build() {
      return new Speaker(this);
    }
  }
}
