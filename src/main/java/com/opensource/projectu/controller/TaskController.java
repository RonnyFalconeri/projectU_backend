package com.opensource.projectu.controller;

import com.opensource.projectu.openapi.api.TasksApi;
import com.opensource.projectu.openapi.model.Task;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class TaskController implements TasksApi {

    @Override
    public ResponseEntity<Task> getTaskById(UUID id) {
        // TODO: implement
        return TasksApi.super.getTaskById(id);
    }

    @Override
    public ResponseEntity<Task> updateTask(UUID id, Task task) {
        // TODO: implement
        return TasksApi.super.updateTask(id, task);
    }

    @Override
    public ResponseEntity<Void> deleteTask(UUID id) {
        // TODO: implement
        return TasksApi.super.deleteTask(id);
    }
}
