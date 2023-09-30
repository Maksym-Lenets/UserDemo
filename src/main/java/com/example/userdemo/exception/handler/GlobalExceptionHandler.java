package com.example.userdemo.exception.handler;

import com.example.userdemo.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GlobalExceptionHandler is a controller advice class that handles exceptions
 * globally for the application. It provides exception handling and response
 * customization for various types of exceptions.
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ErrorAttributes errorAttributes;

    private final ErrorAttributeOptions attributeOptions =
        ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE,
            ErrorAttributeOptions.Include.STACK_TRACE);

    /**
     * Handles NotFoundException and returns a ResponseEntity with a NOT_FOUND
     * status code.
     *
     * @param request The WebRequest containing information about the request.
     * @return ResponseEntity containing an ExceptionResponse with details of the
     *         exception.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(getErrorAttributes(request));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    /**
     * Handles IllegalArgumentException and returns a ResponseEntity with a
     * BAD_REQUEST status code.
     *
     * @param request The WebRequest containing information about the request.
     * @return ResponseEntity containing an ExceptionResponse with details of the
     *         exception.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(getErrorAttributes(request));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
    }

    /**
     * Handles MethodArgumentNotValidException and returns a ResponseEntity with a
     * BAD_REQUEST status code.
     *
     * @param ex The MethodArgumentNotValidException.
     * @return ResponseEntity containing a Map of field errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
            .stream().map(fe -> fe.getField() + " - " + fe.getDefaultMessage()).toList();
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

    private Map<String, Object> getErrorAttributes(WebRequest webRequest) {
        return new HashMap<>(errorAttributes.getErrorAttributes(webRequest, attributeOptions));
    }
}
