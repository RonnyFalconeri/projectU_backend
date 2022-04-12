package com.opensource.projectu.service;

import com.opensource.projectu.openapi.model.Task;
import com.opensource.projectu.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class TaskService {

    private final ProjectRepository projectRepository;

    public Task getTaskById(UUID id) {
        // TODO: implement
        return null;
    }

    public Task updateTask(UUID id, Task task) {
        // TODO: implement
        return null;
    }

    public Task deleteTask(UUID id) {
        // TODO: implement
        return null;
    }
}
