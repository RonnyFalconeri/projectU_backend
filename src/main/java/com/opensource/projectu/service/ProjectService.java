package com.opensource.projectu.service;

import com.opensource.projectu.model.Project;
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
        projectRepository.save(project);
    }

    public void updateProject(String id, Project updatedProject) {
        Optional<Project> project = projectRepository.findById(id);

        if(project.isPresent()) {
            Project projectToUpdate = project.get();

            projectToUpdate.setTitle(updatedProject.getTitle());
            projectToUpdate.setDescription(updatedProject.getDescription());
            projectToUpdate.setSubProjects(updatedProject.getSubProjects());
            projectToUpdate.setState(updatedProject.getState());
            projectToUpdate.setComplexity(updatedProject.getComplexity());
            projectToUpdate.setEstimatedDurationInHours(updatedProject.getEstimatedDurationInHours());
            projectToUpdate.setOwner(updatedProject.getOwner());

            projectRepository.save(projectToUpdate);
        }
    }
}
