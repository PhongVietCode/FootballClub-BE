package com.example.footballclub.exception;

import com.example.footballclub.dto.ApiResponse;
import jakarta.validation.ConstraintViolation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String MIN_ATTRIBUTE = "min";
    private static final String MAX_ATTRIBUTE = "max";
    private static final String ATTRIBUTE_NAME = "name";

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<?>> handlingValidation(MethodArgumentNotValidException exception) {
        String enumKey = Objects.requireNonNull(exception.getFieldError()).getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_CODE_KEY;
        Map<String, Object> attributes;
        errorCode = ErrorCode.valueOf(enumKey);

        //        Get failed constraint
        var constrainViolation =
                exception.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);
        attributes = constrainViolation.getConstraintDescriptor().getAttributes();
        String name = exception.getBindingResult().getFieldError().getField();
        return ResponseEntity.badRequest()
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(
                                Objects.nonNull(attributes)
                                        ? mapAttribute(errorCode.getMessage(), attributes, name)
                                        : errorCode.getMessage())
                        .build());
    }

    private String mapAttribute(String message, Map<String, Object> attributes, String name) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));
        String maxValue = String.valueOf(attributes.get(MAX_ATTRIBUTE));
        return message.replace("{" + ATTRIBUTE_NAME + "}", name)
                .replace("{" + MIN_ATTRIBUTE + "}", minValue)
                .replace("{" + MAX_ATTRIBUTE + "}", maxValue);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<?>> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity.status(errorCode.getStatusCode().value())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse<?>> handlingAccessDenied(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return ResponseEntity.status(errorCode.getStatusCode().value())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<?>> handleMissingRequestBody(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest()
                .body(ApiResponse.builder()
                        .code(ErrorCode.REQUEST_BODY_MISSING.getCode())
                        .message("Request body is required")
                        .build());
    }

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<?>> handlingRuntimeException(RuntimeException exception) {
        log.error(exception.getMessage());
        return ResponseEntity.badRequest()
                .body(ApiResponse.builder()
                        .code(ErrorCode.UNKNOWN_ERROR.getCode())
                        .message(ErrorCode.UNKNOWN_ERROR.getMessage())
                        .build());
    }
}
