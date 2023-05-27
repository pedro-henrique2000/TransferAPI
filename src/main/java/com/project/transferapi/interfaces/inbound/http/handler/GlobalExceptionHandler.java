package com.project.transferapi.interfaces.inbound.http.handler;

import com.project.transferapi.domain.exceptions.BadCredentialsException;
import com.project.transferapi.domain.exceptions.BusinessException;
import com.project.transferapi.domain.exceptions.ConflictException;
import com.project.transferapi.domain.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

   @ExceptionHandler(AccessDeniedException.class)
   @ResponseStatus(HttpStatus.FORBIDDEN)
   public ResponseEntity<ExceptionDetails> handleAccessDenied(final AccessDeniedException accessDeniedException) {
      final ExceptionDetails exceptionDetails = ExceptionDetails.builder()
            .status(HttpStatus.FORBIDDEN.value())
            .details(accessDeniedException.getMessage())
            .title("Forbidden Exception")
            .timestamp(LocalDateTime.now())
            .developerMessage(accessDeniedException.getClass().getName())
            .build();

      return ResponseEntity.status(403).body(exceptionDetails);
   }

   @ExceptionHandler(BadCredentialsException.class)
   @ResponseStatus(HttpStatus.CONFLICT)
   public ResponseEntity<ExceptionDetails> handleBadCredentials(final BadCredentialsException badCredentialsException) {
      final ExceptionDetails exceptionDetails = ExceptionDetails.builder()
            .status(HttpStatus.UNAUTHORIZED.value())
            .details(badCredentialsException.getMessage())
            .title("Unauthorized Exception")
            .timestamp(LocalDateTime.now())
            .developerMessage(badCredentialsException.getClass().getName())
            .build();

      return ResponseEntity.status(401).body(exceptionDetails);
   }

   @ExceptionHandler(ResourceNotFoundException.class)
   @ResponseStatus(HttpStatus.NOT_FOUND)
   public ResponseEntity<ExceptionDetails> handleResourceNotFoundException(final ResourceNotFoundException resourceNotFoundException) {
      final ExceptionDetails exceptionDetails = ExceptionDetails.builder()
            .status(HttpStatus.NOT_FOUND.value())
            .details(resourceNotFoundException.getMessage())
            .title("Not Found Exception")
            .timestamp(LocalDateTime.now())
            .developerMessage(resourceNotFoundException.getClass().getName())
            .build();

      return ResponseEntity.status(404).body(exceptionDetails);
   }

   @ExceptionHandler(ConflictException.class)
   @ResponseStatus(HttpStatus.CONFLICT)
   public ResponseEntity<ExceptionDetails> handleConflictException(final ConflictException conflictException) {
      final ExceptionDetails exceptionDetails = ExceptionDetails.builder()
            .status(HttpStatus.CONFLICT.value())
            .details(conflictException.getMessage())
            .title("Conflict Exception")
            .timestamp(LocalDateTime.now())
            .developerMessage(conflictException.getClass().getName())
            .build();

      return ResponseEntity.status(409).body(exceptionDetails);
   }

   @ExceptionHandler(BusinessException.class)
   @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
   public ResponseEntity<ExceptionDetails> handleBusinessException(final BusinessException exception) {
      final ExceptionDetails exceptionDetails = ExceptionDetails.builder()
            .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
            .details(exception.getMessage())
            .title("Business Exception")
            .timestamp(LocalDateTime.now())
            .developerMessage(exception.getClass().getName())
            .build();

      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY.value()).body(exceptionDetails);
   }

   @ExceptionHandler(Exception.class)
   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   public ResponseEntity<ExceptionDetails> handleInternalException(final Exception exception) {
      exception.printStackTrace();

      final ExceptionDetails exceptionDetails = ExceptionDetails.builder()
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .details(exception.getMessage())
            .title("Internal Error")
            .timestamp(LocalDateTime.now())
            .developerMessage(exception.getClass().getName())
            .build();

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(exceptionDetails);
   }

   @Override
   @ResponseStatus(HttpStatus.BAD_REQUEST)
   protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException methodArgumentNotValidException, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
      List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
      Map<String, String> violations = this.getViolations(fieldErrors);
      ValidationExceptionDetails validationExceptionDetails = ValidationExceptionDetails.builder()
            .details("Check the wrong fields")
            .timestamp(LocalDateTime.now())
            .title("Field Validation Exception")
            .developerMessage(methodArgumentNotValidException.getClass().getName())
            .status(HttpStatus.BAD_REQUEST.value())
            .violations(violations)
            .build();
      return new ResponseEntity<>(validationExceptionDetails, HttpStatus.BAD_REQUEST);
   }

   private Map<String, String> getViolations(List<FieldError> fieldErrors) {
      Map<String, String> violations = new HashMap<>();
      for (FieldError fieldError : fieldErrors) {
         String field = fieldError.getField();
         String message = fieldError.getDefaultMessage();
         violations.put(field, message);
      }
      return violations;
   }

}
