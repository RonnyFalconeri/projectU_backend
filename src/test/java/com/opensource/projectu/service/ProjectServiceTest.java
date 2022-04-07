package com.opensource.projectu.service;

import com.opensource.projectu.exception.ProjectNotFoundException;
import com.opensource.projectu.openapi.model.Complexity;
import com.opensource.projectu.openapi.model.Project;
import com.opensource.projectu.openapi.model.State;
import com.opensource.projectu.openapi.model.Task;
import com.opensource.projectu.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

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
        var mockProjects = buildMockProjects();

        when(projectRepository.findAll())
                .thenReturn(mockProjects);

        var returnedProjects = projectService.getAllProjects();
        int randomIndex = generateRandomInteger(0, mockProjects.size()-1);
        var returnedProject = returnedProjects.get(randomIndex);
        var mockProject = mockProjects.get(randomIndex);

        assertThat(returnedProject).isEqualTo(mockProject);
    }

    private int generateRandomInteger(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    @Test
    void getProjectByIdWithSuccess() {
        var mockProject = buildMockProject();

        when(projectRepository.findById(mockProject.getId()))
                .thenReturn(Optional.of(mockProject));

        var returnedProject = projectService.getProjectById(mockProject.getId());

        assertThat(returnedProject).isEqualTo(mockProject);
    }

    @Test
    void getProjectByIdShouldThrowExceptionWhenProjectNotFound() {
        var mockProject = buildMockProject();

        when(projectRepository.findById(mockProject.getId()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> projectService.getProjectById(mockProject.getId()))
                .isInstanceOf(ProjectNotFoundException.class);
    }

    @Test
    void createProjectShouldReturnProjectWhenSuccessful() {
        var mockProject = buildMockProject();

        when(projectRepository.save(mockProject))
                .thenReturn(mockProject);

        var returnedProject = projectService.createProject(mockProject);

        assertThat(returnedProject).isEqualTo(mockProject);
    }

    @Test
    void updateProjectShouldReturnProjectWhenSuccessful() {
        var mockProject = buildMockProject();

        when(projectRepository.findById(mockProject.getId()))
                .thenReturn(Optional.of(mockProject));

        when(projectRepository.save(mockProject))
                .thenReturn(mockProject);

        var returnedProject = projectService.updateProject(mockProject.getId(), mockProject).getBody();

        assertThat(returnedProject).isEqualTo(mockProject);
    }

    @Test
    void updateProjectShouldReturnProjectWhenProjectNotFound() {
        var mockProject = buildMockProject();

        when(projectRepository.findById(mockProject.getId()))
                .thenReturn(Optional.empty());

        when(projectRepository.save(mockProject))
                .thenReturn(mockProject);

        var returnedProject = projectService.updateProject(mockProject.getId(), mockProject).getBody();

        assertThat(returnedProject).isEqualTo(mockProject);
    }

    @Test
    void deleteProjectShouldNotThrowExceptionWhenProjectFound() {
        var mockProject = buildMockProject();

        when(projectRepository.existsById(mockProject.getId()))
                .thenReturn(true);
        
        assertThatCode(
                () -> projectService.deleteProject(mockProject.getId()))
                .doesNotThrowAnyException();
    }

    @Test
    void deleteProjectShouldThrowExceptionWhenProjectNotFound() {
        var mockProject = buildMockProject();

        when(projectRepository.existsById(mockProject.getId()))
                .thenReturn(false);

        assertThatThrownBy(
                () -> projectService.deleteProject(mockProject.getId()))
                .isInstanceOf(ProjectNotFoundException.class);
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
