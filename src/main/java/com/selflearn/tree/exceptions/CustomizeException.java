package com.selflearn.tree.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

//customize exception by my own easy to use
public class CustomizeException{

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class BadRequestException extends RuntimeException {
        String code;
        public BadRequestException(String message) {
            super(message);
        }
        public BadRequestException(String message, String code) {
            super(message);
            this.code=code;
        }
    }

    @Getter
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class NotFoundException extends RuntimeException {
        String code;
        public NotFoundException(String message) {
            super(message);
        }
        public NotFoundException(String message, String code) {
            super(message);
            this.code = code;
        }

    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    public static class ForbiddenException extends RuntimeException {
        public ForbiddenException(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public static class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String message) {
            super(message);
        }
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    public static class ConflictException extends RuntimeException {
        public ConflictException(String message) {
            super(message);
        }
    }
}
