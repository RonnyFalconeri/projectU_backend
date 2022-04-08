package com.opensource.projectu.util;

import com.opensource.projectu.repository.ProjectRepository;

import java.util.Calendar;
import java.util.UUID;

public final class ProjectPersistenceUtil {

    private ProjectPersistenceUtil() {}

    public static UUID generateUniqueId(UUID id, ProjectRepository projectRepository) {
        if(projectRepository.existsById(id)) {
            return generateUniqueId(UUID.randomUUID(), projectRepository);
        }
        return id;
    }

    public static long getCurrentTimestamp() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }
}
