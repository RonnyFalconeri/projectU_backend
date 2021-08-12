package com.opensource.projectu.controller;

import com.opensource.projectu.model.Project;
import com.opensource.projectu.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("projects")
    public List<Project> all() {
        return projectService.getAllProjects();
    }

    @GetMapping("/projects/{id}")
    Project one(@PathVariable String id) {
        return projectService.getProjectById(id);
    }

    @PostMapping("/projects")
    ResponseEntity<?> create(@RequestBody Project project) {

        projectService.createProject(project);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(project.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
