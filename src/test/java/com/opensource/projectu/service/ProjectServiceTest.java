package com.opensource.projectu.service;

import com.opensource.projectu.openapi.model.Complexity;
import com.opensource.projectu.openapi.model.Project;
import com.opensource.projectu.openapi.model.State;
import com.opensource.projectu.openapi.model.Task;
import com.opensource.projectu.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    ProjectRepository projectRepository;

    ProjectService projectService;

    @BeforeEach
    void setup() {
        projectService = new ProjectService(projectRepository);
    }

    @Test
    void getAllProjectsWithSuccess() {
        var projects = buildTestProjects();

        Mockito.when(projectRepository.findAll())
                .thenReturn(projects);

        var returnedProjects = projectService.getAllProjects();

        assertThat(returnedProjects).hasSize(3);
    }

    private Project buildTestProject() {
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

    private List<Project> buildTestProjects() {
        return new ArrayList<>(Arrays.asList(
                Project.builder()
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
                        .build(),
                Project.builder()
                        .id("2")
                        .title("title2")
                        .description("description2")
                        .state(State.HALTED)
                        .complexity(Complexity.MEDIUM)
                        .estimatedDurationInHours(20)
                        .expectedResult("2 successful unit test")
                        .createdAt("02.01.2022")
                        .startedAt("02.01.2022")
                        .build(),
                Project.builder()
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
                        .build()
        ));
    }
}
