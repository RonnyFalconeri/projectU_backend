package com.opensource.projectu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String id) {super("Project with id "+ id +" not found.");}
}
