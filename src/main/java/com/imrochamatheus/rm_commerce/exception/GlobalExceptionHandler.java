package com.imrochamatheus.rm_commerce.exception;

import com.imrochamatheus.rm_commerce.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ApiError buildApiError (int status, String message, String path) {
        ApiError apiError = new ApiError();
        apiError.setStatus(status);
        apiError.setError(message);
        apiError.setPath(path);
        apiError.setTimestamp(Instant.now());

        return apiError;
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiError apiError = this.buildApiError(status.value(), ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status.value()).body(apiError);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleResourceAlreadyExistsException (
            ResourceAlreadyExistsException ex,
            HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        ApiError apiError = this.buildApiError(status.value(), ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status.value()).body(apiError);
    }

    @ExceptionHandler(ResourceIntegrityViolation.class)
    public ResponseEntity<ApiError> handleResourceIntegrityViolationException (
            ResourceIntegrityViolation ex,
            HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        ApiError apiError = this.buildApiError(status.value(), ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status.value()).body(apiError);
    }
}
