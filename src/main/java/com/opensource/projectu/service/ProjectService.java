package com.opensource.projectu.service;

import com.opensource.projectu.exception.ProjectNotFoundException;
import com.opensource.projectu.openapi.model.Project;
import com.opensource.projectu.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(String id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
    }

    public Project createProject(Project project) {
        project.id(generateUniqueId());
        return projectRepository.save(project);
    }

    private String generateUniqueId() {
        var id = UUID.randomUUID().toString();
        if(projectRepository.existsById(id)) {
            return generateUniqueId();
        }
        return id;
    }

    @Transactional
    public Project updateProject(String id, Project updatedProject) {
        var currentProject = getProjectById(id);

        currentProject.title(updatedProject.getTitle())
                .description(updatedProject.getDescription())
                .tasks(updatedProject.getTasks())
                .state(updatedProject.getState())
                .complexity(updatedProject.getComplexity())
                .estimatedDurationInHours(updatedProject.getEstimatedDurationInHours())
                .expectedResult(updatedProject.getExpectedResult())
                .actualResult(updatedProject.getActualResult())
                .startedAt(updatedProject.getStartedAt())
                .finishedAt(updatedProject.getFinishedAt());

        return projectRepository.save(currentProject);
    }

    public void deleteProject(String id) {
        if(projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
        }
        throw new ProjectNotFoundException(id);
    }
}
