package com.opensource.projectu.controller;

import com.opensource.projectu.model.Project;
import com.opensource.projectu.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("projects")
    public ResponseEntity<List<Project>> all() {
        try {
            List<Project> projects = new ArrayList<Project>(projectService.getAllProjects());

            if (projects.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<Project> one(@PathVariable String id) {
        Optional<Project> projectData = projectService.getProjectById(id);

        if (projectData.isPresent()) {
            return new ResponseEntity<>(projectData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/projects")
    public ResponseEntity<Project> create(@RequestBody Project project) {
        try {
            projectService.createProject(project);
            return new ResponseEntity<>(project, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
