package org.robe.fpa.errorhandling;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.robe.fpa.exceptions.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(basePackages = "org.robe.fpa")
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonNode> defaultErrorHandler(Exception ex, ServletWebRequest request) {
        log.error("Handle unexpected error: {}, Exception: ", requestToString(request.getRequest()), ex);
        return ResponseEntity.internalServerError().body(createErrorMessage(ex));
    }
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessage.builder()
                .message(e.getMessage())
                .build());
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessage.builder()
                .message(e.getMessage())
                .build());
    }
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var result = ex.getBindingResult();
        List<ObjectError> errors = result.getAllErrors();
    
        return ResponseEntity
                .badRequest()
                .body(JsonNodeFactory.instance.objectNode().put("message", 
                                errors.stream()
                                        .map(err -> err.unwrap(ConstraintViolation.class))
                                        .map(ConstraintViolation::getMessage)
                                        .collect(Collectors.joining(", "))));
    }
    
    private String requestToString(HttpServletRequest request) {
        return "Request (Method: " + request.getMethod() + ", URL: " + request.getRequestURI() + ")";
    }
    
    private JsonNode createErrorMessage(Throwable throwable) {
        return JsonNodeFactory.instance.objectNode()
                .put("message", throwable.getMessage())
                .put("stacktrace", ExceptionUtils.getStackTrace(throwable));
    }
}
