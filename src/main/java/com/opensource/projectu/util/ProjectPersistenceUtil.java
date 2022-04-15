package com.opensource.projectu.util;

import com.opensource.projectu.openapi.model.Project;
import com.opensource.projectu.openapi.model.Task;
import com.opensource.projectu.repository.ProjectRepository;

import java.util.Calendar;
import java.util.UUID;

import static com.google.common.collect.Lists.newArrayList;

public final class ProjectPersistenceUtil {

    private ProjectPersistenceUtil() {}

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
}
