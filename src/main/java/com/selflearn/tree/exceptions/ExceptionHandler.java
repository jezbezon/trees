package com.selflearn.tree.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandler {

   @org.springframework.web.bind.annotation.ExceptionHandler(CustomizeException.BadRequestException.class)
    public ResponseEntity<Object> handleNotFoundException(CustomizeException.BadRequestException ex) {
       ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),ex.code,HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @Getter
    public static class ErrorResponse {

        private LocalDateTime timestamp;
        private String code;
        private String message;
        private int status;

        public ErrorResponse(String code, String message,int status) {
            this.code = code;
            this.message = message;
            this.timestamp = LocalDateTime.now();
            this.status = status;
        }

    }

}
