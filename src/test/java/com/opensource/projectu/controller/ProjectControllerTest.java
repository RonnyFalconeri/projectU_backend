package com.opensource.projectu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opensource.projectu.exception.ProjectNotFoundException;
import com.opensource.projectu.openapi.model.Complexity;
import com.opensource.projectu.openapi.model.Project;
import com.opensource.projectu.openapi.model.State;
import com.opensource.projectu.openapi.model.Task;
import com.opensource.projectu.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
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

    @Test
    void getAllProjectsShouldReturnAllProjectsWith200WhenSuccess() throws Exception {
        var mockProjects = buildMockProjects();

        when(projectService.getAllProjects())
                .thenReturn(mockProjects);

        var request = MockMvcRequestBuilders
                .get("/projects")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void getProjectByIdShouldReturnProjectWith200WhenSuccess() throws Exception {
        var mockProject = buildMockProject();

        when(projectService.getProjectById(mockProject.getId()))
                .thenReturn(mockProject);

        var request = MockMvcRequestBuilders
                .get("/projects/{id}", mockProject.getId())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void getProjectByIdShouldReturnExceptionWith404WhenProjectNotFound() throws Exception {
        var mockProject = buildMockProject();

        when(projectService.getProjectById(mockProject.getId()))
                .thenThrow(new ProjectNotFoundException(mockProject.getId()));

        var request = MockMvcRequestBuilders
                .get("/projects/{id}", mockProject.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof ProjectNotFoundException));
    }

    @Test
    void createProjectShouldReturnProjectWith201WhenSuccess() throws Exception {
        var mockProject = buildMockProject();

        when(projectService.createProject(mockProject))
                .thenReturn(mockProject);

        var request = MockMvcRequestBuilders
                .post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockProject));

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void createProjectShouldReturnProjectWith201WhenRequiredFieldsAreThere() throws Exception {
        var mockProject = Project.builder()
                .id(UUID.randomUUID())
                .title("title 1")
                .state(State.IN_PROGRESS)
                .complexity(Complexity.EASY)
                .build();

        when(projectService.createProject(mockProject))
                .thenReturn(mockProject);

        var request = MockMvcRequestBuilders
                .post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockProject));

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void createProjectShouldReturnExceptionWith400WhenTitleIsMissing() throws Exception {
        var invalidMockProject = Project.builder()
                .description("description1")
                .estimatedDurationInHours(10)
                .state(State.IN_PROGRESS)
                .complexity(Complexity.EASY)
                .expectedResult("1 successful unit test")
                .startedAt("01.01.2022")
                .build();

        var request = MockMvcRequestBuilders
                .post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(invalidMockProject));

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("ERROR: Project is invalid. Reason:")))
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")));
    }

    @Test
    void createProjectShouldReturnExceptionWith400WhenStateIsMissing() throws Exception {
        var invalidMockProject = Project.builder()
                .title("qwer")
                .description("description1")
                .estimatedDurationInHours(10)
                .complexity(Complexity.EASY)
                .expectedResult("1 successful unit test")
                .startedAt("01.01.2022")
                .build();

        var request = MockMvcRequestBuilders
                .post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(invalidMockProject));

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("ERROR: Project is invalid. Reason:")))
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")));
    }

    @Test
    void createProjectShouldReturnExceptionWith400WhenComplexityIsMissing() throws Exception {
        var invalidMockProject = Project.builder()
                .title("qwer")
                .description("description1")
                .estimatedDurationInHours(10)
                .state(State.IN_PROGRESS)
                .expectedResult("1 successful unit test")
                .startedAt("01.01.2022")
                .build();

        var request = MockMvcRequestBuilders
                .post("/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(invalidMockProject));

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("ERROR: Project is invalid. Reason:")))
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")));
    }

    @Test
    void updateProjectShouldReturnProjectWith200WhenSuccess() throws Exception {
        var mockProject = buildMockProject();

        when(projectService.updateProject(mockProject.getId(), mockProject))
                .thenReturn(new ResponseEntity<>(mockProject, HttpStatus.OK));

        var request = MockMvcRequestBuilders
                .put("/projects/{id}", mockProject.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockProject));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void updateProjectShouldReturnProjectWith201WhenProjectNotFound() throws Exception {
        var mockProject = buildMockProject();

        when(projectService.updateProject(mockProject.getId(), mockProject))
                .thenReturn(new ResponseEntity<>(mockProject, HttpStatus.CREATED));

        var request = MockMvcRequestBuilders
                .put("/projects/{id}", mockProject.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockProject));

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void updateProjectShouldReturnProjectWith200WhenRequiredFieldsAreThere() throws Exception {
        var mockProject = Project.builder()
                .id(UUID.randomUUID())
                .title("title 1")
                .state(State.IN_PROGRESS)
                .complexity(Complexity.EASY)
                .build();

        when(projectService.updateProject(mockProject.getId(), mockProject))
                .thenReturn(new ResponseEntity<>(mockProject, HttpStatus.OK));

        var request = MockMvcRequestBuilders
                .put("/projects/{id}", mockProject.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(mockProject));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    void updateProjectShouldReturnErrorResponseWith400WhenTitleIsMissing() throws Exception {
        var invalidMockProject = Project.builder()
                .description("description1")
                .estimatedDurationInHours(10)
                .state(State.IN_PROGRESS)
                .complexity(Complexity.EASY)
                .expectedResult("1 successful unit test")
                .startedAt("01.01.2022")
                .build();

        var request = MockMvcRequestBuilders
                .put("/projects/{id}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(invalidMockProject));

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("ERROR: Project is invalid. Reason:")))
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")));
    }

    @Test
    void updateProjectShouldReturnErrorResponseWith400WhenStateIsMissing() throws Exception {
        var invalidMockProject = Project.builder()
                .title("title 1")
                .description("description1")
                .estimatedDurationInHours(10)
                .complexity(Complexity.EASY)
                .expectedResult("1 successful unit test")
                .startedAt("01.01.2022")
                .build();

        var request = MockMvcRequestBuilders
                .put("/projects/{id}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(invalidMockProject));

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("ERROR: Project is invalid. Reason:")))
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")));
    }

    @Test
    void updateProjectShouldReturnErrorResponseWith400WhenComplexityIsMissing() throws Exception {
        var invalidMockProject = Project.builder()
                .title("title 1")
                .description("description1")
                .estimatedDurationInHours(10)
                .state(State.IN_PROGRESS)
                .expectedResult("1 successful unit test")
                .startedAt("01.01.2022")
                .build();

        var request = MockMvcRequestBuilders
                .put("/projects/{id}", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(invalidMockProject));

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("ERROR: Project is invalid. Reason:")))
                .andExpect(jsonPath("$.httpStatus", is("BAD_REQUEST")));
    }

    @Test
    void deleteProjectShouldReturn204WhenSuccess() throws Exception {
        var mockProject = buildMockProject();

        doNothing().when(projectService).deleteProject(mockProject.getId());

        var request = MockMvcRequestBuilders
                .delete("/projects/{id}", mockProject.getId())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteProjectShouldReturnExceptionWith404WhenProjectNotFound() throws Exception {
        var mockProject = buildMockProject();

        doThrow(new ProjectNotFoundException(mockProject.getId()))
                .when(projectService).deleteProject(mockProject.getId());

        var request = MockMvcRequestBuilders
                .delete("/projects/{id}", mockProject.getId())
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof ProjectNotFoundException));
    }

    private Project buildMockProject() {
        return Project.builder()
                .id(UUID.randomUUID())
                .title("title1")
                .description("description1")
                .tasks(Arrays.asList(
                        Task.builder()
                                .id(UUID.randomUUID())
                                .title("task1")
                                .description("task description1")
                                .done(false)
                                .estimatedDurationInHours(1)
                                .build(),
                        Task.builder()
                                .id(UUID.randomUUID())
                                .title("task2")
                                .description("task description2")
                                .done(false)
                                .estimatedDurationInHours(2)
                                .build(),
                        Task.builder()
                                .id(UUID.randomUUID())
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
                .createdAt(1508484583267L)
                .startedAt("01.01.2022")
                .build();
    }

    private List<Project> buildMockProjects() {
        return new ArrayList<>(Arrays.asList(
                Project.builder()
                        .id(UUID.randomUUID())
                        .title("title1")
                        .description("description1")
                        .tasks(Arrays.asList(
                                Task.builder()
                                        .id(UUID.randomUUID())
                                        .title("task1")
                                        .description("task description1")
                                        .done(false)
                                        .estimatedDurationInHours(1)
                                        .build(),
                                Task.builder()
                                        .id(UUID.randomUUID())
                                        .title("task2")
                                        .description("task description2")
                                        .done(false)
                                        .estimatedDurationInHours(2)
                                        .build(),
                                Task.builder()
                                        .id(UUID.randomUUID())
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
                        .createdAt(1508484583267L)
                        .startedAt("01.01.2022")
                        .build(),
                Project.builder()
                        .id(UUID.randomUUID())
                        .title("title2")
                        .description("description2")
                        .state(State.HALTED)
                        .complexity(Complexity.MEDIUM)
                        .estimatedDurationInHours(20)
                        .expectedResult("2 successful unit test")
                        .createdAt(1508484583267L)
                        .startedAt("02.01.2022")
                        .build(),
                Project.builder()
                        .id(UUID.randomUUID())
                        .title("title3")
                        .description("description3")
                        .tasks(Arrays.asList(
                                Task.builder()
                                        .id(UUID.randomUUID())
                                        .title("task1")
                                        .description("task description1")
                                        .done(false)
                                        .estimatedDurationInHours(1)
                                        .build(),
                                Task.builder()
                                        .id(UUID.randomUUID())
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
                        .createdAt(1508484583267L)
                        .startedAt("03.01.2022")
                        .build()
        ));
    }
}
