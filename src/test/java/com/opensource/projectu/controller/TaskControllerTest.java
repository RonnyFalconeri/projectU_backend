package com.opensource.projectu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensource.projectu.exception.TaskNotFoundException;
import com.opensource.projectu.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
}
