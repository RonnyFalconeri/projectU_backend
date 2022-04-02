package com.opensource.projectu.service;

import com.opensource.projectu.openapi.model.Project;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Optional<Project> getProjectById(String id) {
        return projectRepository.findById(id);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public void createProject(Project project) {
        // TODO: check if all necessary fields are set
        // TODO: preset specific fields like state -> always INITIATED at beginning
        // TODO: validate fields
        projectRepository.save(project);
    }

    public void updateProject(String id, Project updatedProject) {
        // TODO: check if all necessary fields are set
        // TODO: validate fields

        Optional<Project> project = projectRepository.findById(id);

        if(project.isPresent()) {
            Project projectToUpdate = project.get();

            projectToUpdate.setTitle(updatedProject.getTitle());
            projectToUpdate.setDescription(updatedProject.getDescription());
            projectToUpdate.setTasks(updatedProject.getTasks());
            projectToUpdate.setState(updatedProject.getState());
            projectToUpdate.setComplexity(updatedProject.getComplexity());
            projectToUpdate.setEstimatedDurationInHours(updatedProject.getEstimatedDurationInHours());

            projectRepository.save(projectToUpdate);
        }
    }

    public void deleteProject(String id) {
        projectRepository.deleteById(id);
    }
}
