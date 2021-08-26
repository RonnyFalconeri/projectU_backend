package com.opensource.projectu.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
//TODO add Document Annotation if repository is modified.
public class Task {
    @Id
    private String id;
    private String title;
    private String description;
    private State state;
    private Complexity complexity;
    private int estimatedDurationInHours;
    private String result;

    public Task(String title,
                   String description,
                   State state,
                   Complexity complexity,
                   int estimatedDurationInHours,
                   String result) {
        this.title = title;
        this.description = description;
        this.state = state;
        this.complexity = complexity;
        this.estimatedDurationInHours = estimatedDurationInHours;
        this.result = result;
    }
}
