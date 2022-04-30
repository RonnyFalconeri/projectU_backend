package com.opensource.projectu.util;

import com.opensource.projectu.openapi.model.Complexity;
import com.opensource.projectu.openapi.model.Project;
import com.opensource.projectu.openapi.model.State;
import com.opensource.projectu.openapi.model.Task;
import com.opensource.projectu.repository.ProjectRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.opensource.projectu.util.ProjectPersistenceUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static testutil.MockTestingUtil.buildMockProject;
import static testutil.MockTestingUtil.buildMockTask;

@ExtendWith(MockitoExtension.class)
class ProjectPersistenceUtilTest {

    @Mock
    ProjectRepository projectRepository;

    @Test
    void generateUniqueIdForProjectShouldReturnSameIdWhenIdDoesNotExist() {
        var mockId = UUID.randomUUID();

        when(projectRepository.existsById(mockId))
                .thenReturn(false);

        var returnedId = ProjectPersistenceUtil.generateUniqueIdForProject(mockId, projectRepository);

        assertThat(returnedId).isEqualTo(mockId);
    }

    @Test
    void generateUniqueIdForProjectShouldReturnDifferentIdWhenIdAlreadyExists() {
        var mockId = UUID.randomUUID();

        when(projectRepository.existsById(mockId))
                .thenReturn(true);

        var returnedId = ProjectPersistenceUtil.generateUniqueIdForProject(mockId, projectRepository);

        assertThat(returnedId).isNotEqualTo(mockId);
    }

    @Test
    void generateUniqueIdForTaskShouldReturnSameIdWhenIdDoesNotExist() {
        var mockId = UUID.randomUUID();

        when(projectRepository.findByTasksId(mockId))
                .thenReturn(Optional.empty());

        var returnedId = ProjectPersistenceUtil.generateUniqueIdForTask(mockId, projectRepository);

        assertThat(returnedId).isEqualTo(mockId);
    }

    @Test
    void generateUniqueIdForTaskShouldReturnDifferentIdWhenIdAlreadyExists() {
        var mockId = UUID.randomUUID();

        when(projectRepository.findByTasksId(mockId))
                .thenReturn(Optional.of(buildMockProject()));

        var returnedId = ProjectPersistenceUtil.generateUniqueIdForTask(mockId, projectRepository);

        assertThat(returnedId).isNotEqualTo(mockId);
    }

    @Test
    void addTaskToProjectShouldReturnProjectIncludingNewTask() {
        var mockProject = buildMockProject();
        var mockTask = buildMockTask();
        var originalTaskCount = mockProject.getTasks().size();
        var returnedProject = ProjectPersistenceUtil.addTaskToProject(mockProject, mockTask);

        assertThat(returnedProject.getTasks())
                .hasSize(originalTaskCount + 1)
                .contains(mockTask);
    }

    @Test
    void overwriteTaskOfProjectShouldReturnProjectIncludingUpdatedTask() {
        var id = UUID.randomUUID();
        var mockProject = Project.builder()
                .tasks(List.of(
                        Task.builder()
                                .id(id)
                                .title("task old")
                                .description("task description old")
                                .done(false)
                                .estimatedDurationInHours(1)
                                .build()
                ))
                .state(State.INITIATED)
                .complexity(Complexity.EASY)
                .build();

        var updatedTask = Task.builder()
                .id(id)
                .title("task new")
                .description("task description new")
                .done(true)
                .estimatedDurationInHours(2)
                .build();

        var returnedProject = overwriteTaskOfProject(id, updatedTask, mockProject);

        assertThat(findTaskOfProjectById(returnedProject, id))
                .contains(updatedTask);
    }

    @Test
    void overwriteTaskOfProjectShouldReturnProjectIncludingUpdatedTaskWhenTaskNotFound() {
        var mockProject = Project.builder()
                .title("title")
                .tasks(Collections.emptyList())
                .state(State.INITIATED)
                .complexity(Complexity.EASY)
                .build();

        var updatedTask = Task.builder()
                .id(UUID.randomUUID())
                .title("task new")
                .description("task description new")
                .done(true)
                .estimatedDurationInHours(2)
                .build();

        var returnedProject = overwriteTaskOfProject(updatedTask.getId(), updatedTask, mockProject);

        assertThat(findTaskOfProjectById(returnedProject, updatedTask.getId()))
                .contains(updatedTask);
    }

    @Test
    void removeTaskWithIdFromProjectShouldReturnProjectWithoutGivenTask() {
        var id = UUID.randomUUID();
        var mockProject = Project.builder()
                .tasks(List.of(
                        Task.builder()
                                .id(id)
                                .title("task old")
                                .description("task description old")
                                .done(false)
                                .estimatedDurationInHours(1)
                                .build()
                ))
                .state(State.INITIATED)
                .complexity(Complexity.EASY)
                .build();

        var returnedProject = removeTaskWithIdFromProject(id, mockProject);

        assertThat(returnedProject.getTasks())
                .isNotNull()
                .isEmpty();
    }

    @Test
    void removeTaskWithIdFromProjectShouldReturnSameProjectWhenTaskNotFound() {
        var mockProject = Project.builder()
                .title("title")
                .tasks(Collections.emptyList())
                .state(State.INITIATED)
                .complexity(Complexity.EASY)
                .build();

        var returnedProject = removeTaskWithIdFromProject(UUID.randomUUID(), mockProject);

        assertThat(returnedProject).isEqualTo(mockProject);
    }
}
