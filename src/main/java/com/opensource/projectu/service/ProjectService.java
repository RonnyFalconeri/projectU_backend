package com.opensource.projectu.service;

import com.opensource.projectu.model.Project;
import com.opensource.projectu.exception.ProjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Project getProjectById(String id) {
        return projectRepository.findProjectById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
    }

    public void createProject(Project project) {
        projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}
