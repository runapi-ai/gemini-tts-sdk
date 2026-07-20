package ai.runapi.geminitts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ai.runapi.core.RequestOptions;
import ai.runapi.core.errors.ValidationException;
import ai.runapi.core.http.HttpRequest;
import ai.runapi.core.http.HttpResponse;
import ai.runapi.core.http.HttpTransport;
import ai.runapi.core.http.JsonRequestBody;
import ai.runapi.core.json.Json;
import ai.runapi.geminitts.types.CompletedTextToSpeechResponse;
import ai.runapi.geminitts.types.TextToSpeechResponse;
import ai.runapi.geminitts.types.CompletedTextToSpeechResponse;
import ai.runapi.geminitts.types.DialogueTurn;
import ai.runapi.geminitts.types.Speaker;
import ai.runapi.geminitts.types.TextToSpeechModel;
import ai.runapi.geminitts.types.TextToSpeechParams;
import ai.runapi.geminitts.types.TextToSpeechResponse;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.util.Collections;
import org.junit.jupiter.api.Test;

class GeminiTtsClientTest {
  @Test
  void builderCreatesClientAndUniversalResources() {
    GeminiTtsClient client = GeminiTtsClient.builder().apiKey("sk-test").build();

    assertNotNull(client.textToSpeech());
    assertNotNull(client.files());
    assertNotNull(client.account());
  }

  @Test
  void openValueClassesSerializeAsScalarStrings() throws Exception {
    String json = Json.mapper().writeValueAsString(new TextToSpeechModel("gemini-2.5-pro-tts"));

    assertEquals("\"gemini-2.5-pro-tts\"", json);
    assertEquals(new TextToSpeechModel("gemini-2.5-pro-tts"), Json.mapper().readValue(json, TextToSpeechModel.class));
  }

  @Test
  void createSendsExpectedRequestShape() throws Exception {
    CapturingTransport transport = new CapturingTransport("{\"id\":\"task_123\",\"status\":\"processing\"}");
    GeminiTtsClient client = GeminiTtsClient.builder().apiKey("sk-test").transport(transport).build();

    client.textToSpeech().create(
        TextToSpeechParams.builder()
            .model(TextToSpeechModel.GEMINI_2_5_PRO_TTS)
            .speakers(java.util.Collections.singletonList(Speaker.builder().speakerId("Speaker 1").voiceName("Fenrir").accent("British (RP)").style("Deadpan").pace("Natural").build()))
            .dialogueTurns(java.util.Collections.singletonList(DialogueTurn.builder().speakerId("Speaker 1").text("Welcome.").build()))
            .build()
    );

    assertEquals("POST", transport.request.getMethod().name());
    assertEquals("/api/v1/gemini_tts/text_to_speech", transport.request.getPath());
    JsonNode body = bodyJson(transport.request);
    assertNotNull(body);
  }

  @Test
  void getDecodesTaskResponseAndExtraFields() {
    CapturingTransport transport = new CapturingTransport("{\"id\":\"task_456\",\"status\":\"completed\",\"audios\":[{\"url\":\"https://file.runapi.ai/generated\"}],\"custom\":\"kept\"}");
    GeminiTtsClient client = GeminiTtsClient.builder().apiKey("sk-test").transport(transport).build();

    TextToSpeechResponse response = client.textToSpeech().get("task_456");

    assertEquals("GET", transport.request.getMethod().name());
    assertEquals("/api/v1/gemini_tts/text_to_speech/task_456", transport.request.getPath());
    assertEquals("completed", response.getStatus().value());
    assertNotNull(response.getAudios());
    assertEquals("kept", response.extraFields().get("custom").asText());
  }

  @Test
  void runPollsUntilCompletedAndKeepsExtraFields() {
    SequenceTransport transport = new SequenceTransport(
        "{\"id\":\"task_789\",\"status\":\"processing\"}",
        "{\"id\":\"task_789\",\"status\":\"completed\",\"audios\":[{\"url\":\"https://file.runapi.ai/generated\"}],\"custom\":\"kept\"}");
    GeminiTtsClient client = GeminiTtsClient.builder().apiKey("sk-test").transport(transport).build();

    CompletedTextToSpeechResponse response = client.textToSpeech().run(
        TextToSpeechParams.builder()
            .model(TextToSpeechModel.GEMINI_2_5_PRO_TTS)
            .speakers(java.util.Collections.singletonList(Speaker.builder().speakerId("Speaker 1").voiceName("Fenrir").accent("British (RP)").style("Deadpan").pace("Natural").build()))
            .dialogueTurns(java.util.Collections.singletonList(DialogueTurn.builder().speakerId("Speaker 1").text("Welcome.").build()))
            .build(),
        RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build());

    assertEquals("completed", response.getStatus().value());
    assertNotNull(response.getAudios());
    assertEquals("kept", response.extraFields().get("custom").asText());
    assertEquals(2, transport.calls);
  }

  @Test
  void runRejectsCompletedResponseMissingResultField() {
    SequenceTransport transport = new SequenceTransport(
        "{\"id\":\"task_missing\",\"status\":\"processing\"}",
        "{\"id\":\"task_missing\",\"status\":\"completed\"}");
    GeminiTtsClient client = GeminiTtsClient.builder().apiKey("sk-test").transport(transport).build();

    assertThrows(
        ValidationException.class,
        () -> client.textToSpeech().run(
                TextToSpeechParams.builder()
                    .model(TextToSpeechModel.GEMINI_2_5_PRO_TTS)
                    .speakers(java.util.Collections.singletonList(Speaker.builder().speakerId("Speaker 1").voiceName("Fenrir").accent("British (RP)").style("Deadpan").pace("Natural").build()))
                    .dialogueTurns(java.util.Collections.singletonList(DialogueTurn.builder().speakerId("Speaker 1").text("Welcome.").build()))
                    .build(),
            RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build()));
  }

    @Test
    void coversTexttospeechResourceMethods() {
      CapturingTransport createTransport = new CapturingTransport("{\"id\":\"task_text_to_speech\",\"status\":\"processing\"}");
      GeminiTtsClient createClient = GeminiTtsClient.builder().apiKey("sk-test").transport(createTransport).build();
      assertNotNull(createClient.textToSpeech().create(
              TextToSpeechParams.builder()
                  .model(TextToSpeechModel.GEMINI_2_5_PRO_TTS)
                  .speakers(java.util.Collections.singletonList(Speaker.builder().speakerId("Speaker 1").voiceName("Fenrir").accent("British (RP)").style("Deadpan").pace("Natural").build()))
                  .dialogueTurns(java.util.Collections.singletonList(DialogueTurn.builder().speakerId("Speaker 1").text("Welcome.").build()))
                  .build()
      ));

      CapturingTransport createWithOptionsTransport = new CapturingTransport("{\"id\":\"task_text_to_speech_options\",\"status\":\"processing\"}");
      GeminiTtsClient createWithOptionsClient = GeminiTtsClient.builder().apiKey("sk-test").transport(createWithOptionsTransport).build();
      assertNotNull(createWithOptionsClient.textToSpeech().create(
              TextToSpeechParams.builder()
                  .model(TextToSpeechModel.GEMINI_2_5_PRO_TTS)
                  .speakers(java.util.Collections.singletonList(Speaker.builder().speakerId("Speaker 1").voiceName("Fenrir").accent("British (RP)").style("Deadpan").pace("Natural").build()))
                  .dialogueTurns(java.util.Collections.singletonList(DialogueTurn.builder().speakerId("Speaker 1").text("Welcome.").build()))
                  .build(),
          RequestOptions.none()));

      CapturingTransport getTransport = new CapturingTransport("{\"id\":\"task_text_to_speech\",\"status\":\"completed\",\"audios\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      GeminiTtsClient getClient = GeminiTtsClient.builder().apiKey("sk-test").transport(getTransport).build();
      assertNotNull(getClient.textToSpeech().get("task_text_to_speech"));

      CapturingTransport getWithOptionsTransport = new CapturingTransport("{\"id\":\"task_text_to_speech_options\",\"status\":\"completed\",\"audios\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      GeminiTtsClient getWithOptionsClient = GeminiTtsClient.builder().apiKey("sk-test").transport(getWithOptionsTransport).build();
      assertNotNull(getWithOptionsClient.textToSpeech().get("task_text_to_speech_options", RequestOptions.none()));

      SequenceTransport runTransport = new SequenceTransport(
          "{\"id\":\"task_text_to_speech_run\",\"status\":\"processing\"}",
          "{\"id\":\"task_text_to_speech_run\",\"status\":\"completed\",\"audios\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      GeminiTtsClient runClient = GeminiTtsClient.builder().apiKey("sk-test").transport(runTransport).build();
      CompletedTextToSpeechResponse runResponse = runClient.textToSpeech().run(
              TextToSpeechParams.builder()
                  .model(TextToSpeechModel.GEMINI_2_5_PRO_TTS)
                  .speakers(java.util.Collections.singletonList(Speaker.builder().speakerId("Speaker 1").voiceName("Fenrir").accent("British (RP)").style("Deadpan").pace("Natural").build()))
                  .dialogueTurns(java.util.Collections.singletonList(DialogueTurn.builder().speakerId("Speaker 1").text("Welcome.").build()))
                  .build(),
          RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build());
      assertNotNull(runResponse);

      SequenceTransport runWithOptionsTransport = new SequenceTransport(
          "{\"id\":\"task_text_to_speech_run_options\",\"status\":\"processing\"}",
          "{\"id\":\"task_text_to_speech_run_options\",\"status\":\"completed\",\"audios\":[{\"url\":\"https://file.runapi.ai/generated\"}]}");
      GeminiTtsClient runWithOptionsClient = GeminiTtsClient.builder().apiKey("sk-test").transport(runWithOptionsTransport).build();
      assertNotNull(runWithOptionsClient.textToSpeech().run(
              TextToSpeechParams.builder()
                  .model(TextToSpeechModel.GEMINI_2_5_PRO_TTS)
                  .speakers(java.util.Collections.singletonList(Speaker.builder().speakerId("Speaker 1").voiceName("Fenrir").accent("British (RP)").style("Deadpan").pace("Natural").build()))
                  .dialogueTurns(java.util.Collections.singletonList(DialogueTurn.builder().speakerId("Speaker 1").text("Welcome.").build()))
                  .build(),
          RequestOptions.builder().pollingInterval(Duration.ofMillis(1)).pollingMaxWait(Duration.ofSeconds(1)).build()));
    }

  private static JsonNode bodyJson(HttpRequest request) throws Exception {
    JsonRequestBody body = (JsonRequestBody) request.getBody();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    body.writeTo(out);
    return Json.mapper().readTree(out.toByteArray());
  }

  private static final class CapturingTransport implements HttpTransport {
    private final String body;
    private HttpRequest request;

    private CapturingTransport(String body) {
      this.body = body;
    }

    public HttpResponse send(HttpRequest request) {
      this.request = request;
      return new HttpResponse(200, body, Collections.<String, java.util.List<String>>emptyMap());
    }

    public void close() {}
  }

  private static final class SequenceTransport implements HttpTransport {
    private final String[] responses;
    private int calls;

    private SequenceTransport(String... responses) {
      this.responses = responses;
    }

    public HttpResponse send(HttpRequest request) {
      String response = responses[Math.min(calls, responses.length - 1)];
      calls++;
      return new HttpResponse(200, response, Collections.<String, java.util.List<String>>emptyMap());
    }

    public void close() {}
  }
}
