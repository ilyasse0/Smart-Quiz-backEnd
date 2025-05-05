package net.bensitel.smartquiz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.http.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class HuggingFaceService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final PromptEngineeringService promptEngineeringService;


    @Value("${huggingface.api.token}")
    private String apiToken;

    @Value("${huggingface.model.url}")
    private String modelUrl;



    public String generateQcm(String inputText) {

        String prompt = promptEngineeringService.buildQcmPrompt(inputText);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + apiToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Creating a more detailed request body with parameters
        Map<String, Object> body = new HashMap<>();
        body.put("inputs", prompt);
        body.put("parameters", Map.of(
                "max_length", 1024,
                "temperature", 0.7,
                "do_sample", true
        ));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                modelUrl,
                HttpMethod.POST,
                request,
                String.class
        );

        // The response is a JSON array with a "generated_text" field
        // For now, we'll just return the raw response and handle parsing later
        return parseHuggingFaceResponse(response.getBody());
    }

    private String parseHuggingFaceResponse(String responseBody) {
        // Simple parsing - remove the JSON wrapper to get just the generated text
        if (responseBody == null || responseBody.isEmpty()) {
            return "Error: Empty response from model";
        }

        // Basic extraction of the generated_text field
        if (responseBody.contains("generated_text")) {
            // Extract content between the first "generated_text":"  and the next "
            int startIndex = responseBody.indexOf("generated_text") + 17; // 17 is length of "generated_text":"
            int endIndex = responseBody.indexOf("\"}", startIndex);

            if (startIndex > 0 && endIndex > startIndex) {
                return responseBody.substring(startIndex, endIndex)
                        .replace("\\n", "\n")  // Handle newlines properly
                        .replace("\\\"", "\"") // Handle escaped quotes
                        .replace("\\\\", "\\"); // Handle escaped backslashes
            }
        }

        // If we can't parse it properly, return the raw response
        return "Failed to parse response: " + responseBody;
    }

}
