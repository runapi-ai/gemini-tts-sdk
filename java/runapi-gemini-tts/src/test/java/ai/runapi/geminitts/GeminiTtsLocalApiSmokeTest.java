    package ai.runapi.geminitts;

    import static org.junit.jupiter.api.Assertions.assertEquals;
    import static org.junit.jupiter.api.Assertions.assertNotNull;

    import ai.runapi.core.Constants;
    import ai.runapi.core.RequestOptions;
    import ai.runapi.core.json.Json;
    import ai.runapi.geminitts.types.CompletedTextToSpeechResponse;
    import ai.runapi.geminitts.types.CompletedTextToSpeechResponse;
import ai.runapi.geminitts.types.DialogueTurn;
import ai.runapi.geminitts.types.Speaker;
import ai.runapi.geminitts.types.TextToSpeechModel;
import ai.runapi.geminitts.types.TextToSpeechParams;
import ai.runapi.geminitts.types.TextToSpeechResponse;
    import com.fasterxml.jackson.databind.JsonNode;
    import com.sun.net.httpserver.HttpExchange;
    import com.sun.net.httpserver.HttpServer;
    import java.io.ByteArrayOutputStream;
    import java.io.IOException;
    import java.io.OutputStream;
    import java.net.InetSocketAddress;
    import java.nio.charset.StandardCharsets;
    import java.time.Duration;
    import java.util.ArrayList;
    import java.util.List;
    import org.junit.jupiter.api.AfterEach;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;

    class GeminiTtsLocalApiSmokeTest {
      private HttpServer server;
      private final List<CapturedRequest> requests = new ArrayList<CapturedRequest>();

      @BeforeEach
      void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/api/v1/gemini_tts/text_to_speech", this::handleRequest);
        server.start();
      }

      @AfterEach
      void stopServer() {
        server.stop(0);
      }

      @Test
      void runUsesApacheTransportAgainstLocalApi() throws Exception {
        try (GeminiTtsClient client =
            GeminiTtsClient.builder()
                .apiKey("sk-test")
                .baseUrl("http://127.0.0.1:" + server.getAddress().getPort())
                .build()) {
      CompletedTextToSpeechResponse response =
          client.textToSpeech().run(
              TextToSpeechParams.builder()
                  .model(TextToSpeechModel.GEMINI_2_5_PRO_TTS)
                  .speakers(java.util.Collections.singletonList(Speaker.builder().speakerId("Speaker 1").voiceName("Fenrir").accent("British (RP)").style("Deadpan").pace("Natural").build()))
                  .dialogueTurns(java.util.Collections.singletonList(DialogueTurn.builder().speakerId("Speaker 1").text("Welcome.").build()))
                  .build(),
              RequestOptions.builder()
                  .pollingInterval(Duration.ofMillis(1))
                  .pollingMaxWait(Duration.ofSeconds(2))
                  .maxRetries(0)
                  .build());

          assertEquals("completed", response.getStatus().value());
          assertNotNull(response.getAudios());
          assertEquals("kept", response.extraFields().get("custom").asText());
        }

        assertEquals(2, requests.size());
        CapturedRequest create = requests.get(0);
        assertEquals("POST", create.method);
        assertEquals("/api/v1/gemini_tts/text_to_speech", create.path);
        assertEquals("Bearer sk-test", create.header("Authorization"));
        assertEquals(Constants.SDK_USER_AGENT, create.header("User-Agent"));
        JsonNode body = Json.mapper().readTree(create.body);
        assertNotNull(body);
      }

      private void handleRequest(HttpExchange exchange) throws IOException {
        CapturedRequest captured = CapturedRequest.from(exchange);
        requests.add(captured);

        if ("POST".equals(captured.method)) {
          write(exchange, 200, "{\"id\":\"task_local_123\",\"status\":\"processing\"}");
          return;
        }

        assertEquals("/api/v1/gemini_tts/text_to_speech/task_local_123", captured.path);
        write(exchange, 200, "{\"id\":\"task_local_123\",\"status\":\"completed\",\"audios\":[{\"url\":\"https://file.runapi.ai/generated\"}],\"custom\":\"kept\"}");
      }

      private static void write(HttpExchange exchange, int status, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream out = exchange.getResponseBody()) {
          out.write(bytes);
        }
      }

      private static final class CapturedRequest {
        private final String method;
        private final String path;
        private final com.sun.net.httpserver.Headers headers;
        private final String body;

        private CapturedRequest(String method, String path, com.sun.net.httpserver.Headers headers, String body) {
          this.method = method;
          this.path = path;
          this.headers = headers;
          this.body = body;
        }

        private static CapturedRequest from(HttpExchange exchange) throws IOException {
          return new CapturedRequest(
              exchange.getRequestMethod(),
              exchange.getRequestURI().getPath(),
              exchange.getRequestHeaders(),
              new String(readAll(exchange), StandardCharsets.UTF_8));
        }

        private String header(String name) {
          List<String> values = headers.get(name);
          if (values == null || values.isEmpty()) {
            return null;
          }
          return values.get(0);
        }

        private static byte[] readAll(HttpExchange exchange) throws IOException {
          ByteArrayOutputStream out = new ByteArrayOutputStream();
          byte[] buffer = new byte[1024];
          int read;
          while ((read = exchange.getRequestBody().read(buffer)) != -1) {
            out.write(buffer, 0, read);
          }
          return out.toByteArray();
        }
      }
    }
