package com.opensource.projectu.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static testutil.MockTestingUtil.buildMockProject;
import static testutil.MockTestingUtil.buildMockProjects;

@DataMongoTest
class ProjectRepositoryTest {

    @Autowired
    ProjectRepository projectRepository;

    @Disabled("A proper way to test the repository has yet to be found.")
    @Test
    void findByIdShouldReturnExpectedProject() {
        var mockProject = buildMockProject();

        projectRepository.save(mockProject);

        var returnedProject = projectRepository.findById(mockProject.getId());

        assertThat(returnedProject)
                .isPresent()
                .contains(mockProject);
    }

    @Disabled("A proper way to test the repository has yet to be found.")
    @Test
    void findByIdShouldReturnEmptyOptionalWhenNoProjectFound() {
        var returnedProject = projectRepository.findById(UUID.randomUUID());

        assertThat(returnedProject).isEmpty();
    }

    @Disabled("A proper way to test the repository has yet to be found.")
    @Test
    void findAllShouldReturnEveryProject() {
        var mockProjects = buildMockProjects();

        projectRepository.saveAll(mockProjects);

        var returnedProjects = projectRepository.findAll();

        assertThat(returnedProjects)
                .hasSameSizeAs(mockProjects)
                .isEqualTo(mockProjects);
    }
}
