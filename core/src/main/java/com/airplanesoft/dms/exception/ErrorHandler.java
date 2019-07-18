package com.airplanesoft.dms.exception;

import com.airplanesoft.dms.http.RestResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<RestResponse> handleEntityNotFound(EntityNotFoundException ex) {
        RestResponse restResponse = new RestResponse(false, Collections.singletonList(ex.getMessage()));
        return buildResponseEntity(restResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<RestResponse> handleException(Exception ex) {
        RestResponse restResponse = new RestResponse(false, Collections.singletonList(ex.getMessage()));
        return buildResponseEntity(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<RestResponse> buildResponseEntity(RestResponse<?> restResponse, HttpStatus status) {
        return new ResponseEntity<>(restResponse, status);
    }
}
