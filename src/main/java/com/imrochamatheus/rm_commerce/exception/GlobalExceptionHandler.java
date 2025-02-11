package com.imrochamatheus.rm_commerce.exception;

import com.imrochamatheus.rm_commerce.dto.ApiErrorDTO;
import com.imrochamatheus.rm_commerce.dto.ValidationErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ApiErrorDTO buildApiError (int status, String message, String path) {
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO();
        apiErrorDTO.setStatus(status);
        apiErrorDTO.setError(message);
        apiErrorDTO.setPath(path);
        apiErrorDTO.setTimestamp(Instant.now());

        return apiErrorDTO;
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiErrorDTO apiErrorDTO = this.buildApiError(status.value(), ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status.value()).body(apiErrorDTO);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiErrorDTO> handleResourceAlreadyExistsException (
            ResourceAlreadyExistsException ex,
            HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        ApiErrorDTO apiErrorDTO = this.buildApiError(status.value(), ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status.value()).body(apiErrorDTO);
    }

    @ExceptionHandler(ResourceIntegrityViolation.class)
    public ResponseEntity<ApiErrorDTO> handleResourceIntegrityViolationException (
            ResourceIntegrityViolation ex,
            HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        ApiErrorDTO apiErrorDTO = this.buildApiError(status.value(), ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status.value()).body(apiErrorDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDTO> handleMethodArgumentNotValidException (
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

        ValidationErrorDTO validationError = new ValidationErrorDTO();
        validationError.setError("Validation error");
        validationError.setPath(request.getRequestURI());
        validationError.setStatus(status.value());
        validationError.setTimestamp(Instant.now());

        ex.getBindingResult().getFieldErrors().forEach(x -> {
            validationError.addError(x.getField(), x.getDefaultMessage());
        });

        return ResponseEntity.status(status.value()).body(validationError);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorDTO> handleMissingServletRequestParameterException (
            MissingServletRequestParameterException ex,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ApiErrorDTO apiErrorDTO = this.buildApiError(status.value(), ex.getBody().getDetail(), request.getRequestURI());

        return ResponseEntity.status(status.value()).body(apiErrorDTO);
    }
}
