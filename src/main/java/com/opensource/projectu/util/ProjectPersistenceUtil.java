package com.opensource.projectu.util;

import com.opensource.projectu.openapi.model.Project;
import com.opensource.projectu.openapi.model.Task;
import com.opensource.projectu.repository.ProjectRepository;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

public final class ProjectPersistenceUtil {

    public static UUID generateUniqueId(UUID id, ProjectRepository projectRepository) {
        if(projectRepository.existsById(id)) {
            return generateUniqueId(UUID.randomUUID(), projectRepository);
        }
        return id;
    }

    public static long getCurrentTimestamp() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static Project addTaskToProject(Project project, Task task) {
        var taskList = newArrayList(project.getTasks());
        taskList.add(task);
        project.tasks(taskList);
        return project;
    }

    public static Project overwriteTaskOfProject(UUID taskId, Task task, Project project) {
        return addTaskToProject(
                removeTaskWithIdFromProject(taskId, project),
                task);
    }

    public static Optional<Task> findTaskOfProjectById(Project project, UUID id) {
        return project.getTasks()
                .stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }

    protected static Project removeTaskWithIdFromProject(UUID taskId, Project project) {
        return project.tasks(project.getTasks()
                .stream()
                .filter(task -> !task.getId().equals(taskId))
                .collect(Collectors.toList()));
    }

    private ProjectPersistenceUtil() {}
}
