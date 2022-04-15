package com.opensource.projectu.controller;

import com.opensource.projectu.openapi.api.TasksApi;
import com.opensource.projectu.openapi.model.Task;
import com.opensource.projectu.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class TaskController implements TasksApi {

    private final TaskService taskService;

    @Override
    public ResponseEntity<Task> getTaskById(UUID id) {
        return new ResponseEntity<>(taskService.getTaskById(id), HttpStatus.OK);
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
