    package ai.runapi.geminitts;

    import static org.junit.jupiter.api.Assertions.assertEquals;
    import static org.junit.jupiter.api.Assertions.assertNotNull;
    import static org.junit.jupiter.api.Assumptions.assumeTrue;

        import ai.runapi.core.errors.TaskFailedException;
    import ai.runapi.core.RequestOptions;
    import ai.runapi.core.json.Json;
    import ai.runapi.geminitts.types.CompletedTextToSpeechResponse;
    import ai.runapi.geminitts.types.CompletedTextToSpeechResponse;
import ai.runapi.geminitts.types.DialogueTurn;
import ai.runapi.geminitts.types.Speaker;
import ai.runapi.geminitts.types.TextToSpeechModel;
import ai.runapi.geminitts.types.TextToSpeechParams;
import ai.runapi.geminitts.types.TextToSpeechResponse;
    import com.fasterxml.jackson.databind.node.ObjectNode;
    import java.nio.charset.StandardCharsets;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.time.Duration;
    import org.junit.jupiter.api.Test;

    class GeminiTtsLiveApiSmokeTest {
      @Test
      void primaryResourceRunAgainstLiveRunApi() throws Exception {
        assumeTrue("true".equals(System.getenv("RUNAPI_JAVA_LIVE_GEMINI_TTS_SMOKE")));

        String baseUrl = requireEnv("RUNAPI_BASE_URL");
        String apiKey = requireEnv("RUNAPI_API_KEY");
        String callbackUrl = callbackUrl("gemini-tts");
        Path outputPath = Paths.get(System.getenv().getOrDefault("RUNAPI_JAVA_LIVE_GEMINI_TTS_OUTPUT", "build/live-gemini-tts-smoke-result.json"));
        Files.createDirectories(outputPath.getParent());
        try (GeminiTtsClient client = GeminiTtsClient.builder().apiKey(apiKey).baseUrl(baseUrl).build()) {
          ObjectNode result = Json.mapper().createObjectNode();
          result.put("action", "gemini-tts/text-to-speech");
          result.put("result_field", "audios");
          result.put("callback_url", callbackUrl);
          try {
      CompletedTextToSpeechResponse response =
          client.textToSpeech().run(
              TextToSpeechParams.builder()
                  .model(TextToSpeechModel.GEMINI_2_5_PRO_TTS)
                  .speakers(java.util.Collections.singletonList(Speaker.builder().speakerId("Speaker 1").voiceName("Fenrir").accent("British (RP)").style("Deadpan").pace("Natural").build()))
                  .dialogueTurns(java.util.Collections.singletonList(DialogueTurn.builder().speakerId("Speaker 1").text("Welcome.").build()))
                  .callbackUrl(callbackUrl)
                  .build(),
              RequestOptions.builder()
                  .pollingInterval(Duration.ofSeconds(10))
                  .pollingMaxWait(Duration.ofMinutes(15))
                  .maxRetries(0)
                  .build());

          assertEquals("completed", response.getStatus().value());
            assertNotNull(response.getAudios());
            result.put("id", response.getId());
            result.put("status", response.getStatus().value());
            Files.write(outputPath, Json.mapper().writerWithDefaultPrettyPrinter().writeValueAsString(result).getBytes(StandardCharsets.UTF_8));
          } catch (TaskFailedException failure) {
            result.put("status", "failed");
            result.put("exception", failure.getClass().getSimpleName());
            result.put("message", failure.getMessage());
            Object taskResponse = failure.getTaskResponse();
            if (taskResponse instanceof TextToSpeechResponse) {
              result.put("id", ((TextToSpeechResponse) taskResponse).getId());
              result.put("status", ((TextToSpeechResponse) taskResponse).getStatus().value());
              result.put("error", ((TextToSpeechResponse) taskResponse).getError());
            }
            Files.write(outputPath, Json.mapper().writerWithDefaultPrettyPrinter().writeValueAsString(result).getBytes(StandardCharsets.UTF_8));
            throw failure;
          } catch (RuntimeException failure) {
            result.put("status", "error");
            result.put("exception", failure.getClass().getSimpleName());
            result.put("message", failure.getMessage());
            Files.write(outputPath, Json.mapper().writerWithDefaultPrettyPrinter().writeValueAsString(result).getBytes(StandardCharsets.UTF_8));
            throw failure;
          }
        }
      }

      private static String callbackUrl(String modelSlug) {
        String base = requireEnv("RUNAPI_CALLBACK_URL");
        String normalized = base.endsWith("/") ? base.substring(0, base.length() - 1) : base;
        return normalized + "/java-live-smoke/" + modelSlug + "/" + System.currentTimeMillis();
      }

      private static String requireEnv(String name) {
        String value = System.getenv(name);
        if (value == null || value.trim().isEmpty()) {
          throw new IllegalStateException(name + " is required");
        }
        return value;
      }
    }
