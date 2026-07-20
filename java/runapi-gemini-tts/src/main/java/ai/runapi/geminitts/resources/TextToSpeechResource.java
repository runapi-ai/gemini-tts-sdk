package ai.runapi.geminitts.resources;

import ai.runapi.core.ClientOptions;
import ai.runapi.core.RequestOptions;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.core.polling.TaskCreateResponse;
import ai.runapi.geminitts.types.CompletedTextToSpeechResponse;
import ai.runapi.geminitts.types.TextToSpeechParams;
import ai.runapi.geminitts.types.TextToSpeechResponse;

/** Text To Speech operations. */
public final class TextToSpeechResource extends GeminittsResource {
  /** API endpoint path for text to speech operations. */
  public static final String ENDPOINT = "/api/v1/gemini_tts/text_to_speech";

  /** Creates a resource bound to the supplied transport and client options. */
  public TextToSpeechResource(HttpTransport transport, ClientOptions options) {
    super(transport, options, ENDPOINT);
  }

  /** Creates a text to speech task. */
  public TaskCreateResponse create(TextToSpeechParams params) {
    return create(params, RequestOptions.none());
  }

  /** Creates a text to speech task with per-request options. */
  public TaskCreateResponse create(TextToSpeechParams params, RequestOptions options) {
    return createTask(params.action(), params.toMap(), options);
  }

  /** Retrieves a text to speech task by ID. */
  public TextToSpeechResponse get(String id) {
    return get(id, RequestOptions.none());
  }

  /** Retrieves a text to speech task by ID with per-request options. */
  public TextToSpeechResponse get(String id, RequestOptions options) {
    return getTask(id, options, TextToSpeechResponse.class);
  }

  /** Creates a text to speech task and polls until it completes. */
  public CompletedTextToSpeechResponse run(TextToSpeechParams params) {
    return run(params, RequestOptions.none());
  }

  /** Creates a text to speech task with per-request options and polls until it completes. */
  public CompletedTextToSpeechResponse run(TextToSpeechParams params, RequestOptions options) {
    return runTask(params.action(), params.toMap(), options, TextToSpeechResponse.class, CompletedTextToSpeechResponse.class);
  }
}
