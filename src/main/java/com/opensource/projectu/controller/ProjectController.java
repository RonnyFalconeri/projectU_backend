package com.opensource.projectu.controller;

import com.opensource.projectu.openapi.model.Project;
import com.opensource.projectu.openapi.api.ProjectsApi;
import com.opensource.projectu.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class ProjectController implements ProjectsApi {

    private final ProjectService projectService;

    @Override
    public ResponseEntity<List<Project>> getAllProjects() {
        try {
            List<Project> projects = projectService.getAllProjects();

            if (projects.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Project> getProjectById(String id) {
        Optional<Project> projectData = projectService.getProjectById(id);

        if (projectData.isPresent()) {
            return new ResponseEntity<>(projectData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Project> createProject(Project project) {
        try {
            projectService.createProject(project);
            return new ResponseEntity<>(project, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Project> updateProject(String id, Project project) {
        Optional<Project> projectData = projectService.getProjectById(id);

        if (projectData.isPresent()) {
            projectService.updateProject(id, project);
            return new ResponseEntity<>(project, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Void> deleteProject(String id) {
        try {
            projectService.deleteProject(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
