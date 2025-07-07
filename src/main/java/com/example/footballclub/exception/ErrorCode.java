package com.example.footballclub.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNKNOWN_ERROR("UNKNOWN_ERROR", 9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_CODE_KEY("INVALID_CODE_KEY", 1001, "Invalid message key", HttpStatus.BAD_REQUEST),
    USER_EXISTED("USER_EXISTED", 1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID("USERNAME_INVALID", 1003, "Username must be at least 4 characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD("INVALID_PASSWORD", 1004, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    INVALID_USER("INVALID_USER", 1005, "User not found", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED("UNAUTHENTICATED", 1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("UNAUTHORIZED", 1007, "You do not have permission", HttpStatus.UNAUTHORIZED),
    INVALID_DOB("INVALID_DOB", 1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    INVALID_ELO("INVALID_ELO", 1009, "Member's must be in range from {min} to {max}", HttpStatus.BAD_REQUEST),
    INVALID_ORGANIZATION("INVALID_ORGANIZATION", 1010, "Your organization is not found", HttpStatus.BAD_REQUEST),
    INVALID_MEMBER("INVALID_MEMBER", 1011, "Member is not exist", HttpStatus.BAD_REQUEST),
    INVALID_STRING_LENGTH("INVALID_STRING_LENGTH", 1012, "Value should have length from {min} to {max}", HttpStatus.BAD_REQUEST),
    INVALID_ADDRESS("INVALID_ADDRESS", 1013, "Address is not valid", HttpStatus.BAD_REQUEST),
    INVALID_CONTEST("INVALID_CONTEST", 1014, "Contest is not found", HttpStatus.BAD_REQUEST),
    TEAM_COUNT_IS_NOT_VALID("TEAM_COUNT_IS_NOT_VALID", 1015, "Team count must be divided by number of player", HttpStatus.BAD_REQUEST),
    REQUEST_BODY_MISSING("REQUEST_BODY_MISSING", 1016, "This method needs data in body", HttpStatus.BAD_REQUEST),
    ATTRIBUTE_NOT_NULL("ATTRIBUTE_NOT_NULL", 1017,"{name} should not null", HttpStatus.BAD_REQUEST),
    DUPLICATED_ORG_NAME("DUPLICATED_ORG_NAME", 1018,"This name is not available", HttpStatus.BAD_REQUEST)
    ;
    private int code;
    private String message;
    private HttpStatusCode statusCode;
    private String name;

    ErrorCode(String name, int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    public ErrorCode setCode(int code) {
        this.code = code;
        return this;
    }

    public ErrorCode setMessage(String message) {
        this.message = message;
        return this;
    }

    public ErrorCode setStatusCode(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
