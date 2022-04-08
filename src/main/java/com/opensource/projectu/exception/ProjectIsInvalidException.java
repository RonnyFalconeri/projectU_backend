package com.opensource.projectu.exception;

import com.opensource.projectu.openapi.model.Project;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectIsInvalidException extends RuntimeException {
    public ProjectIsInvalidException(Project project) {
        super("Project is invalid. Project: "+ project.toString());
    }
}
