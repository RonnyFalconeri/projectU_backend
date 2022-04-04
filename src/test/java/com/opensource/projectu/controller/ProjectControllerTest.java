package com.opensource.projectu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensource.projectu.exception.ProjectNotFoundException;
import com.opensource.projectu.openapi.model.Complexity;
import com.opensource.projectu.openapi.model.Project;
import com.opensource.projectu.openapi.model.State;
import com.opensource.projectu.openapi.model.Task;
import com.opensource.projectu.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    ProjectService projectService;

    Project testProject1 = buildTestProject1();

    Project testProject2 = buildTestProject2();

    Project testProject3 = buildTestProject3();

    @Test
    void getAllProjectsWithSuccess() throws Exception {
        var projects = new ArrayList<>(
                Arrays.asList(testProject1, testProject2, testProject3));

        Mockito.when(projectService.getAllProjects())
                .thenReturn(projects);

        var request = MockMvcRequestBuilders
                .get("/projects")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].id", is("3")))
                .andExpect(jsonPath("$[2].title", is("title3")))
                .andExpect(jsonPath("$[2].description", is("description3")))
                .andExpect(jsonPath("$[2].tasks[1].title", is("task2")))
                .andExpect(jsonPath("$[2].state", is("FINISHED")))
                .andExpect(jsonPath("$[2].complexity", is("DIFFICULT")))
                .andExpect(jsonPath("$[2].estimatedDurationInHours", is(30)));
    }

    @Test
    void getProjectByIdWithSuccess() throws Exception {
        var project = testProject1;

        Mockito.when(projectService.getProjectById(project.getId()))
                .thenReturn(project);

        var request = MockMvcRequestBuilders
                .get("/projects/" + project.getId())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.title", is(project.getTitle())));
    }

    @Test
    void createProjectWithSuccess() throws Exception {
        var project = testProject1;

        Mockito.when(projectService.createProject(project))
                .thenReturn(project);

        var request = MockMvcRequestBuilders
                .post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(project));

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.description", is(project.getDescription())));
    }

    @Test
    void updateProjectWithSuccess() throws Exception {
        var project = testProject3;

        Mockito.when(projectService.getProjectById(project.getId()))
                .thenReturn(project);

        Mockito.when(projectService.updateProject(project.getId(), project))
                .thenReturn(project);

        var request = MockMvcRequestBuilders
                .put("/projects/" + project.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(project));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.title", is(project.getTitle())))
                .andExpect(jsonPath("$.tasks[0].title", is(project.getTasks().get(0).getTitle())));
    }

    @Test
    void updateProjectWithProjectNotFound() throws Exception {
        var project = testProject2;

        Mockito.when(projectService.updateProject(project.getId(), project))
                .thenThrow(new ProjectNotFoundException(project.getId()));

        var request = MockMvcRequestBuilders
                .put("/projects/" + project.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(project));

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof ProjectNotFoundException))
                .andExpect(result ->
                        assertEquals("Project with id "+ project.getId() +" not found.", result.getResolvedException().getMessage()));
    }

    @Test
    void deleteProjectWithSuccess() {
        // TODO: implement
    }

    private Project buildTestProject1() {
        return Project.builder()
                .id("1")
                .title("title1")
                .description("description1")
                .tasks(Arrays.asList(
                        Task.builder()
                                .id("1")
                                .title("task1")
                                .description("task description1")
                                .done(false)
                                .estimatedDurationInHours(1)
                                .build(),
                        Task.builder()
                                .id("2")
                                .title("task2")
                                .description("task description2")
                                .done(false)
                                .estimatedDurationInHours(2)
                                .build(),
                        Task.builder()
                                .id("1")
                                .title("task1")
                                .description("task description1")
                                .done(false)
                                .estimatedDurationInHours(3)
                                .build()
                ))
                .state(State.INITIATED)
                .complexity(Complexity.EASY)
                .estimatedDurationInHours(10)
                .expectedResult("1 successful unit test")
                .createdAt("01.01.2022")
                .startedAt("01.01.2022")
                .build();
    }

    private Project buildTestProject2() {
        return Project.builder()
                .id("2")
                .title("title2")
                .description("description2")
                .state(State.HALTED)
                .complexity(Complexity.MEDIUM)
                .estimatedDurationInHours(20)
                .expectedResult("2 successful unit test")
                .createdAt("02.01.2022")
                .startedAt("02.01.2022")
                .build();
    }

    private Project buildTestProject3() {
        return Project.builder()
                .id("3")
                .title("title3")
                .description("description3")
                .tasks(Arrays.asList(
                        Task.builder()
                                .id("1")
                                .title("task1")
                                .description("task description1")
                                .done(false)
                                .estimatedDurationInHours(1)
                                .build(),
                        Task.builder()
                                .id("2")
                                .title("task2")
                                .description("task description2")
                                .done(false)
                                .estimatedDurationInHours(2)
                                .build()
                ))
                .state(State.FINISHED)
                .complexity(Complexity.DIFFICULT)
                .estimatedDurationInHours(30)
                .expectedResult("3 successful unit test")
                .createdAt("03.01.2022")
                .startedAt("03.01.2022")
                .build();
    }
}
