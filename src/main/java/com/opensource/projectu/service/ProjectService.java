package com.opensource.projectu.service;

import com.opensource.projectu.exception.ProjectNotFoundException;
import com.opensource.projectu.openapi.model.Project;
import com.opensource.projectu.openapi.model.Task;
import com.opensource.projectu.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.opensource.projectu.util.ProjectPersistenceUtil.*;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(UUID id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
    }

    public Project createProject(Project project) {
        return projectRepository.save(project
                .id(generateUniqueIdForProject(UUID.randomUUID(), projectRepository))
                .createdAt(getCurrentTimestamp()));
    }

    @Transactional
    public ResponseEntity<Project> updateProject(UUID id, Project updatedProject) {
        return projectRepository.findById(id)
                .map(p -> new ResponseEntity<>(
                        overwriteCurrentProject(p, updatedProject),
                        HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(
                        insertNewProject(id, updatedProject),
                        HttpStatus.CREATED));
    }

    private Project overwriteCurrentProject(Project currentProject, Project updatedProject) {
        return projectRepository.save(currentProject
                .title(updatedProject.getTitle())
                .description(updatedProject.getDescription())
                .tasks(updatedProject.getTasks())
                .state(updatedProject.getState())
                .complexity(updatedProject.getComplexity())
                .estimatedDurationInHours(updatedProject.getEstimatedDurationInHours())
                .expectedResult(updatedProject.getExpectedResult())
                .actualResult(updatedProject.getActualResult())
                .startedAt(updatedProject.getStartedAt())
                .finishedAt(updatedProject.getFinishedAt()));
    }

    private Project insertNewProject(UUID id, Project project) {
        return projectRepository.save(project
                .id(id)
                .createdAt(getCurrentTimestamp()));
    }

    public void deleteProject(UUID id) {
        if(projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
        } else {
            throw new ProjectNotFoundException(id);
        }
    }

    public Project createTask(UUID id, Task task) {
        return projectRepository.findById(id)
                .map(project -> {
                    task.id(generateUniqueIdForTask(UUID.randomUUID(), projectRepository));
                    return projectRepository.save(addTaskToProject(project, task));
                })
                .orElseThrow(() -> new ProjectNotFoundException(id));
    }
}
