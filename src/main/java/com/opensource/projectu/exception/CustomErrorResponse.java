package com.opensource.projectu.exception;

import lombok.Builder;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Builder
@Value
public class CustomErrorResponse {
    String message;
    HttpStatus httpStatus;
    ZonedDateTime timestamp;
}
