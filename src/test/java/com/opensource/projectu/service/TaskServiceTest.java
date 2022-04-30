package com.opensource.projectu.service;

import com.opensource.projectu.exception.TaskNotFoundException;
import com.opensource.projectu.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;
import static testutil.MockTestingUtil.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    ProjectRepository projectRepository;

    TaskService taskService;

    @BeforeEach
    void setup() {
        taskService = new TaskService(projectRepository);
    }

    @Test
    void getTaskByIdShouldReturnTaskWhenTaskFound() {
        var mockTask = buildMockTask();

        when(projectRepository.findByTasksId(mockTask.getId()))
                .thenReturn(Optional.of(buildProjectContainingTask(mockTask)));

        var returnedTask = taskService.getTaskById(mockTask.getId());

        assertThat(returnedTask).isEqualTo(mockTask);
    }

    @Test
    void getTaskByIdShouldThrowExceptionWhenTaskNotFound() {
        var id = UUID.randomUUID();

        when(projectRepository.findByTasksId(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> taskService.getTaskById(id))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void updateTaskShouldReturnTaskWhenTaskFound() {
        var mockTask = buildMockTask();

        when(projectRepository.findByTasksId(mockTask.getId()))
                .thenReturn(Optional.of(buildProjectContainingTask(mockTask)));

        var returnedTask = taskService.updateTask(mockTask.getId(), mockTask);

        assertThat(returnedTask).isEqualTo(mockTask);
    }

    @Test
    void updateTaskShouldThrowExceptionWhenTaskNotFound() {
        var id = UUID.randomUUID();
        var mockTask = buildMockTask();

        when(projectRepository.findByTasksId(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> taskService.updateTask(id, mockTask))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void deleteTaskShouldReturnProjectWhenTaskFound() {
        var mockTask = buildMockTask();

        var mockProject = buildProjectContainingTask(mockTask);

        when(projectRepository.findByTasksId(mockTask.getId()))
                .thenReturn(Optional.of(mockProject));

        when(projectRepository.save(mockProject))
                .thenReturn(mockProject);

        var returnedProject = taskService.deleteTask(mockTask.getId());

        assertThat(returnedProject.getTasks())
                .isEmpty();
    }

    @Test
    void deleteTaskShouldThrowExceptionWhenTaskNotFound() {
        var id = UUID.randomUUID();

        when(projectRepository.findByTasksId(id))
                .thenReturn(Optional.empty());

        assertThatThrownBy(
                () -> taskService.deleteTask(id))
                .isInstanceOf(TaskNotFoundException.class);
    }
}
