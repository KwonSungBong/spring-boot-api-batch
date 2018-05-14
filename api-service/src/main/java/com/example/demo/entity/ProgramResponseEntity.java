package com.example.demo.entity;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
public class ProgramResponseEntity {

    private Program program;
    private String requestUrl;
    private HttpStatus httpStatus;
    private double elapsedSecond;
    private ResponseEntity<User[]> responseEntity;
    private Exception exception;

    public ProgramResponseEntity(Program program, String requestUrl, HttpStatus httpStatus,
                                 double elapsedSecond, ResponseEntity<User[]> responseEntity) {
        this.program = program;
        this.requestUrl = requestUrl;
        this.httpStatus = httpStatus;
        this.elapsedSecond = elapsedSecond;
        this.responseEntity = responseEntity;
    }

    public ProgramResponseEntity(Program program, String requestUrl, HttpStatus httpStatus,
                                 double elapsedSecond, Exception exception) {
        this.program = program;
        this.requestUrl = requestUrl;
        this.httpStatus = httpStatus;
        this.elapsedSecond = elapsedSecond;
        this.exception = exception;
    }

}
