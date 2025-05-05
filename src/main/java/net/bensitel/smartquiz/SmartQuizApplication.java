package net.bensitel.smartquiz;

import org.springframework.ai.model.vertexai.autoconfigure.gemini.VertexAiGeminiChatAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
        VertexAiGeminiChatAutoConfiguration.class
})
public class SmartQuizApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartQuizApplication.class, args);
    }

}
