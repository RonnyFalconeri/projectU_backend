package com.opensource.projectu.service;

import com.opensource.projectu.exception.TaskNotFoundException;
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
        return projectRepository.findByTasksId(id)
                .flatMap(project -> project.getTasks()
                        .stream()
                        .filter(task -> task.getId().equals(id))
                        .findFirst())
                .orElseThrow(() -> new TaskNotFoundException(id));
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
