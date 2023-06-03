package com.arunbalachandran.ehealth.exception;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiExceptionDTO {

    private final String message;
    private final Throwable throwable;
    private final HttpStatus httpStatus;
}
