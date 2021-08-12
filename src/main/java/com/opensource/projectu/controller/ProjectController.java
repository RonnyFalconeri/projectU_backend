package com.opensource.projectu.controller;

import com.opensource.projectu.model.Project;
import com.opensource.projectu.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
