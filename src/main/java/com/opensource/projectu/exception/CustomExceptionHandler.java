package com.opensource.projectu.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ProjectNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleProjectNotFound(ProjectNotFoundException e) {
        var status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(CustomErrorResponse.builder()
                    .message(e.getMessage())
                    .httpStatus(status)
                    .timestamp(ZonedDateTime.now())
                    .build(),
                status);
    }

    @ExceptionHandler(value = TaskNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleTaskNotFound(TaskNotFoundException e) {
        var status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(CustomErrorResponse.builder()
                .message(e.getMessage())
                .httpStatus(status)
                .timestamp(ZonedDateTime.now())
                .build(),
                status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleOtherExceptions(Exception e) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(CustomErrorResponse.builder()
                .message("Unexpected Error: " + e.getMessage())
                .httpStatus(status)
                .timestamp(ZonedDateTime.now())
                .build(),
                status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        return new ResponseEntity<>(CustomErrorResponse.builder()
                    .message("ERROR: Project is invalid. Reason: " + e.getMessage())
                    .httpStatus(status)
                    .timestamp(ZonedDateTime.now())
                    .build(),
                status);
    }
}
