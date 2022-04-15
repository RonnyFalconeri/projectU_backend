package com.opensource.projectu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensource.projectu.exception.TaskNotFoundException;
import com.opensource.projectu.openapi.model.Task;
import com.opensource.projectu.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static testutil.MockTestingUtil.buildMockProject;
import static testutil.MockTestingUtil.buildMockTask;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    TaskService taskService;

    @Test
    void getTaskByIdShouldReturnProjectWith200WhenTaskFound() throws Exception {
        var mockTask = buildMockTask();

        when(taskService.getTaskById(mockTask.getId()))
                .thenReturn(mockTask);

        var request = MockMvcRequestBuilders
                .get("/tasks/{id}", mockTask.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void getTaskByIdShouldReturnExceptionWith404WhenTaskNotFound() throws Exception {
        var id = UUID.randomUUID();

        when(taskService.getTaskById(id))
                .thenThrow(new TaskNotFoundException(id));

        var request = MockMvcRequestBuilders
                .get("/tasks/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof TaskNotFoundException));
    }

    @Test
    void updateTaskShouldReturnTaskWith200WhenTaskFound() throws Exception {
        var mockTask = buildMockTask();

        when(taskService.updateTask(mockTask.getId(), mockTask))
                .thenReturn(mockTask);

        var request = MockMvcRequestBuilders
                .put("/tasks/{id}", mockTask.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockTask));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void updateTaskShouldReturnExceptionWith404WhenTaskNotFound() throws Exception {
        var mockTask = buildMockTask();

        when(taskService.updateTask(mockTask.getId(), mockTask))
                .thenThrow(new TaskNotFoundException(mockTask.getId()));

        var request = MockMvcRequestBuilders
                .put("/tasks/{id}", mockTask.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockTask));

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof TaskNotFoundException));
    }

    @Test
    void updateTaskShouldReturnErrorResponseWith400WhenTitleIsMissing() throws Exception {
        var invalidTask = Task.builder()
                .description("a description")
                .done(false)
                .estimatedDurationInHours(12)
                .build();

        var request = MockMvcRequestBuilders
                .put("/tasks/{id}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(invalidTask));

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("ERROR: Project is invalid. Reason:")))
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")));
    }

    @Test
    void updateTaskShouldReturnErrorResponseWith400WhenDoneIsMissing() throws Exception {
        var invalidTask = Task.builder()
                .title("a title")
                .description("a description")
                .estimatedDurationInHours(12)
                .build();

        var request = MockMvcRequestBuilders
                .put("/tasks/{id}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(invalidTask));

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("ERROR: Project is invalid. Reason:")))
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")));
    }

    @Test
    void deleteTaskShouldReturnProjectWith204WhenTaskFound() throws Exception {
        var mockProject = buildMockProject();
        var id = UUID.randomUUID();

        when(taskService.deleteTask(id))
                .thenReturn(mockProject);

        var request = MockMvcRequestBuilders
                .delete("/tasks/{id}", id)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void deleteTaskShouldReturnExceptionWith404WhenTaskNotFound() throws Exception {
        var id = UUID.randomUUID();

        when(taskService.deleteTask(id))
                .thenThrow(new TaskNotFoundException(id));

        var request = MockMvcRequestBuilders
                .delete("/tasks/{id}", id)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof TaskNotFoundException));
    }
}
