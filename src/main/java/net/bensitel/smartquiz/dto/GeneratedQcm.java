package net.bensitel.smartquiz.dto;

import lombok.Data;
import java.util.List;

@Data
public class GeneratedQcm {
    private String title;
    private String topic;
    private List<GeneratedQuestion> questions;
}

