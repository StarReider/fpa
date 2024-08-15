package org.robe.fpa.errorhandling;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.robe.fpa.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
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
    
    private String requestToString(HttpServletRequest request) {
        return "Request (Method: " + request.getMethod() + ", URL: " + request.getRequestURI() + ")";
    }
    
    private JsonNode createErrorMessage(Throwable throwable) {
        return JsonNodeFactory.instance.objectNode()
                .put("message", throwable.getMessage())
                .put("stacktrace", ExceptionUtils.getStackTrace(throwable));
    }
}
