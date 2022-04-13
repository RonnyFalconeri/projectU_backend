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
import static testutil.MockTestingUtil.buildMockProject;

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

    @Test
    void findByIdShouldReturnEmptyOptionalWhenNoProjectFound() {
        var returnedProject = projectRepository.findById(UUID.randomUUID());

        assertThat(returnedProject).isEmpty();
    }

    @Test
    void findAllShouldReturnEveryProject() {

    }
}
