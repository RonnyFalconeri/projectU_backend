package com.opensource.projectu.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
public class Project {
    @Id
    private String id;
    private String title;
    private String description;
    private List<Task> tasks;
    private State state;
    private Complexity complexity;
    private int estimatedDurationInHours;
    private LocalDateTime createdAt;
    private String expectedResult;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private String actualResult;

    public Project(String title,
                   String description,
                   List<Task> tasks,
                   State state,
                   Complexity complexity,
                   int estimatedDurationInHours,
                   LocalDateTime createdAt,
                   String expectedResult) {
        this.title = title;
        this.description = description;
        this.tasks = tasks;
        this.state = state;
        this.complexity = complexity;
        this.estimatedDurationInHours = estimatedDurationInHours;
        this.createdAt = createdAt;
        this.expectedResult = expectedResult;
    }
}
