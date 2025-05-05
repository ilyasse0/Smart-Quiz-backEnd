package net.bensitel.smartquiz.dto;

import lombok.Data;
import net.bensitel.smartquiz.entity.enums.Difficulty;

import java.util.Map;

@Data
public class GeneratedQuestion {
    private String content;
    private Map<Character, String> options; // A, B, C, D → "Paris", etc.
    private char correctOption;
    private Difficulty difficulty;
}