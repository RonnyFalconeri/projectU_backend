package com.opensource.projectu.service;

import com.opensource.projectu.exception.ProjectNotFoundException;
import com.opensource.projectu.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;
import static testutil.MockTestingUtil.buildMockProject;
import static testutil.MockTestingUtil.buildMockProjects;

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

    private int generateRandomInteger(int max) {
        return (int) (Math.random() * ((max) + 1));
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
        var mockProject = buildMockProject();

        when(projectRepository.findById(mockProject.getId()))
                .thenReturn(Optional.empty());

        var id = mockProject.getId();

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

        var id = mockProject.getId();

        assertThatThrownBy(
                () -> projectService.deleteProject(id))
                .isInstanceOf(ProjectNotFoundException.class);
    }
}
