package net.bensitel.smartquiz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromptEngineeringServiceImp  implements PromptEngineeringService{
    @Override
    public String buildQcmPrompt(String inputText) {

        // todo --> better handling the prompt later
        // for now just a simple prompt to generate a simple 5 question qcm
        return """
        <instruction>
        You are a professional education content creator. Given the input text, generate exactly 5 multiple-choice questions (QCM).
        Follow these rules precisely:
        - Each question must be directly related to important concepts in the given text
        - Provide exactly 4 options labeled A, B, C, and D for each question
        - Make sure only one option is correct
        - After each question and its options, indicate the correct answer
        - Format each question consistently as shown in the example
        - Make questions challenging but fair
        </instruction>
        
        <example>
        Question 1: What is the capital of France?
        A) London
        B) Berlin
        C) Paris
        D) Madrid
        Correct answer: C
        
        Question 2: Who wrote "Romeo and Juliet"?
        A) Charles Dickens
        B) William Shakespeare
        C) Jane Austen
        D) Mark Twain
        Correct answer: B
        </example>
        
        <input_text>
        """ + inputText + """
        </input_text>
        
        Generate 5 multiple-choice questions based on the input text:
        """;
    }
}
