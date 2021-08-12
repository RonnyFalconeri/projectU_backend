package com.opensource.projectu.exception;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String id) {
        super("Could not find project: " + id);
    }
}
