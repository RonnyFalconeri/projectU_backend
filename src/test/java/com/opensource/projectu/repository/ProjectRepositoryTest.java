package com.opensource.projectu.repository;

import com.opensource.projectu.openapi.model.Complexity;
import com.opensource.projectu.openapi.model.Project;
import com.opensource.projectu.openapi.model.State;
import com.opensource.projectu.openapi.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class ProjectRepositoryTest {

    @Autowired
    ProjectRepository projectRepository;

    @Test
    void findByIdShouldReturnExpectedProject() {
        var mockProject = buildMockProject();

        projectRepository.save(mockProject);

        var returnedProject = projectRepository.findById(mockProject.getId());

        assertThat(returnedProject)
                .isPresent()
                .contains(mockProject);
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
}
