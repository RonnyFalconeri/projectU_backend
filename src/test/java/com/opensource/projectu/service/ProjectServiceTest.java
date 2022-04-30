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
import static testutil.MockTestingUtil.*;

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
        int randomIndex = generateRandomInteger(mockProjects.size()-1);
        var returnedProject = returnedProjects.get(randomIndex);
        var mockProject = mockProjects.get(randomIndex);

        assertThat(returnedProjects).hasSameSizeAs(mockProjects);
        assertThat(returnedProject).isEqualTo(mockProject);
    }

    @Test
    void getProjectByIdShouldReturnProjectWhenProjectFound() {
        var mockProject = buildMockProject();

        when(projectRepository.findById(mockProject.getId()))
                .thenReturn(Optional.of(mockProject));

        var returnedProject = projectService.getProjectById(mockProject.getId());

        assertThat(returnedProject).isEqualTo(mockProject);
    }

    @Test
    void getProjectByIdShouldThrowExceptionWhenProjectNotFound() {
        var id = UUID.randomUUID();

        when(projectRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> projectService.getProjectById(id))
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
    void updateProjectShouldReturnProjectWhenProjectFound() {
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
        var id = UUID.randomUUID();

        when(projectRepository.existsById(id))
                .thenReturn(true);
        
        assertThatCode(
                () -> projectService.deleteProject(id))
                .doesNotThrowAnyException();
    }

    @Test
    void deleteProjectShouldThrowExceptionWhenProjectNotFound() {
        var id = UUID.randomUUID();

        when(projectRepository.existsById(id))
                .thenReturn(false);

        assertThatThrownBy(
                () -> projectService.deleteProject(id))
                .isInstanceOf(ProjectNotFoundException.class);
    }

    @Test
    void createTaskShouldReturnProjectWithNewTaskWhenSuccess() {
        var mockProject = buildMockProject();
        var mockTask = buildMockTask();
        var originalTaskCount = mockProject.getTasks().size();

        when(projectRepository.findById(mockProject.getId()))
                .thenReturn(Optional.of(mockProject));

        when(projectRepository.save(mockProject))
                .thenReturn(mockProject);

        var returnedProject = projectService.createTask(mockProject.getId(), mockTask);

        assertThat(returnedProject.getTasks())
                .hasSize(originalTaskCount + 1)
                .contains(mockTask);
    }

    @Test
    void createTaskShouldReturnProjectIncludingNewTaskWithGeneratedUniqueId() {
        var mockProject = Project.builder()
                .id(UUID.randomUUID())
                .title("title1")
                .tasks(Collections.emptyList())
                .state(State.INITIATED)
                .complexity(Complexity.EASY)
                .build();

        var mockTaskWithoutId = Task.builder()
                .title("new task")
                .description("task description new")
                .done(false)
                .estimatedDurationInHours(30)
                .build();

        when(projectRepository.findById(mockProject.getId()))
                .thenReturn(Optional.of(mockProject));

        when(projectRepository.save(mockProject))
                .thenReturn(mockProject);

        var returnedProject = projectService.createTask(mockProject.getId(), mockTaskWithoutId);
        var returnedTask = returnedProject.getTasks().get(0);

        assertThat(returnedTask.getId()).isNotNull();
    }

    @Test
    void createTaskShouldThrowExceptionWhenProjectNotFound() {
        var id = UUID.randomUUID();
        var mockTask = buildMockTask();

        when(projectRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> projectService.createTask(id, mockTask))
                .isInstanceOf(ProjectNotFoundException.class);
    }
}
