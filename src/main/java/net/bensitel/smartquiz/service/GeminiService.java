package net.bensitel.smartquiz.service;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeminiService {
    private final RestTemplate restTemplate;
    private final PromptEngineeringService promptEngineeringService;
    private final Gson gson = new Gson();

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    public String generateQcm(String inputText) {
        String prompt = promptEngineeringService.buildQcmPrompt(inputText);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Gemini API key can be passed as a query parameter or in headers
        // Using query parameter approach as it's more common with Gemini
        String urlWithKey = apiUrl + "?key=" + apiKey;

        // Gemini request structure
        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> content = new HashMap<>();
        Map<String, String> part = new HashMap<>();

        part.put("text", prompt);
        content.put("parts", new Map[] {part});
        requestBody.put("contents", new Map[] {content});

        // You can add generation config if needed
        Map<String, Object> generationConfig = new HashMap<>();
        generationConfig.put("maxOutputTokens", 1024);
        generationConfig.put("temperature", 0.7);
        requestBody.put("generationConfig", generationConfig);

        HttpEntity<String> request = new HttpEntity<>(gson.toJson(requestBody), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                urlWithKey,
                HttpMethod.POST,
                request,
                String.class
        );

        return parseGeminiResponse(response.getBody());
    }

    private String parseGeminiResponse(String responseBody) {
        try {
            // Parse the JSON response
            Map<String, Object> responseMap = gson.fromJson(responseBody, Map.class);

            // Extract candidates
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseMap.get("candidates");
            if (candidates == null || candidates.isEmpty()) {
                return "Error: No candidates found in Gemini response.";
            }

            Map<String, Object> firstCandidate = candidates.get(0);
            Map<String, Object> content = (Map<String, Object>) firstCandidate.get("content");

            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
            if (parts == null || parts.isEmpty()) {
                return "Error: No parts found in Gemini response content.";
            }

            Map<String, Object> firstPart = parts.get(0);
            return (String) firstPart.get("text");

        } catch (Exception e) {
            return "Error parsing Gemini response: " + e.getMessage() + "\nRaw response: " + responseBody;
        }
    }
}
