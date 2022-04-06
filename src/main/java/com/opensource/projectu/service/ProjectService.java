package com.opensource.projectu.service;

import com.opensource.projectu.exception.ProjectNotFoundException;
import com.opensource.projectu.openapi.model.Project;
import com.opensource.projectu.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public Project getProjectById(UUID id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
    }

    public Project createProject(Project project) {
        project.id(generateUniqueId());
        return projectRepository.save(project);
    }

    private UUID generateUniqueId() {
        var id = UUID.randomUUID();
        if(projectRepository.existsById(id)) {
            return generateUniqueId();
        }
        return id;
    }

    @Transactional
    public ResponseEntity<Project> updateProject(UUID id, Project updatedProject) {
        return projectRepository.findById(id)
                .map(project -> new ResponseEntity<>(projectRepository.save(
                        project.title(updatedProject.getTitle())
                            .description(updatedProject.getDescription())
                            .tasks(updatedProject.getTasks())
                            .state(updatedProject.getState())
                            .complexity(updatedProject.getComplexity())
                            .estimatedDurationInHours(updatedProject.getEstimatedDurationInHours())
                            .expectedResult(updatedProject.getExpectedResult())
                            .actualResult(updatedProject.getActualResult())
                            .startedAt(updatedProject.getStartedAt())
                            .finishedAt(updatedProject.getFinishedAt())),
                        HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(projectRepository.save(updatedProject.id(id)),
                                HttpStatus.CREATED));
    }

    public void deleteProject(UUID id) {
        if(projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
        }
        throw new ProjectNotFoundException(id);
    }
}
