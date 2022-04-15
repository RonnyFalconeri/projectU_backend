package com.opensource.projectu.service;

import com.opensource.projectu.exception.TaskNotFoundException;
import com.opensource.projectu.openapi.model.Task;
import com.opensource.projectu.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.opensource.projectu.util.ProjectPersistenceUtil.findTaskOfProjectById;
import static com.opensource.projectu.util.ProjectPersistenceUtil.overwriteTaskOfProject;

@Service
@AllArgsConstructor
public class TaskService {

    private final ProjectRepository projectRepository;

    public Task getTaskById(UUID id) {
        return projectRepository.findByTasksId(id)
                .flatMap(project -> findTaskOfProjectById(project, id))
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task updateTask(UUID id, Task task) {
        return projectRepository.findByTasksId(id)
                .map(project -> { projectRepository.save(
                    overwriteTaskOfProject(id, task, project));
                    return task;
                })
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task deleteTask(UUID id) {
        // TODO: implement
        return null;
    }
}
