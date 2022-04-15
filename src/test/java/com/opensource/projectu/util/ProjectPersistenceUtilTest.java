package com.opensource.projectu.util;

import com.opensource.projectu.repository.ProjectRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static testutil.MockTestingUtil.buildMockProject;
import static testutil.MockTestingUtil.buildMockTask;

@ExtendWith(MockitoExtension.class)
class ProjectPersistenceUtilTest {

    @Mock
    ProjectRepository projectRepository;

    @Test
    void generateUniqueIdShouldReturnSameIdWhenIdDoesNotExist() {
        var mockId = UUID.randomUUID();

        when(projectRepository.existsById(mockId))
                .thenReturn(false);

        var returnedId = ProjectPersistenceUtil.generateUniqueId(mockId, projectRepository);

        assertThat(returnedId).isEqualTo(mockId);
    }

    @Test
    void generateUniqueIdShouldReturnDifferentIdWhenIdAlreadyExists() {
        var mockId = UUID.randomUUID();

        when(projectRepository.existsById(mockId))
                .thenReturn(true);

        var returnedId = ProjectPersistenceUtil.generateUniqueId(mockId, projectRepository);

        assertThat(returnedId).isNotEqualTo(mockId);
    }

    @Disabled("A proper date format has yet to be found.")
    @Test
    void getCurrentTimestampShouldReturnTimestampFormat() {
        // TODO: implement
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
}
