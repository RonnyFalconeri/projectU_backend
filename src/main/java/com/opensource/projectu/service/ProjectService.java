package com.opensource.projectu.service;

import com.opensource.projectu.exception.NotFoundException;
import com.opensource.projectu.openapi.model.Project;
import com.opensource.projectu.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Project getProjectById(String id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project with id "+ id +" not found."));
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project createProject(Project project) {
        project.setId(UUID.randomUUID().toString());
        return projectRepository.save(project);
    }

    public Project updateProject(String id, Project newProject) {
        var currentProject = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project with id "+ id +" not found."));

        var project = copyProjectProperties(newProject, currentProject);
        return projectRepository.save(project);
    }

    private Project copyProjectProperties(Project newProject, Project currentProject) {
        currentProject.setTitle(newProject.getTitle());
        currentProject.setDescription(newProject.getDescription());
        currentProject.setTasks(newProject.getTasks());
        currentProject.setState(newProject.getState());
        currentProject.setComplexity(newProject.getComplexity());
        currentProject.setEstimatedDurationInHours(newProject.getEstimatedDurationInHours());
        currentProject.setExpectedResult(newProject.getExpectedResult());
        currentProject.setActualResult(newProject.getActualResult());
        currentProject.setStartedAt(newProject.getStartedAt());
        currentProject.setFinishedAt(newProject.getFinishedAt());
        return currentProject;
    }

    public void deleteProject(String id) {
        projectRepository.deleteById(id);
    }
}
