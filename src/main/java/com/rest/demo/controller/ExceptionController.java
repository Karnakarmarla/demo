package com.rest.demo.controller;

import com.rest.demo.EmptyInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NoSuchFieldException.class)
    public ResponseEntity<String> handleNoSuchFieldException(NoSuchFieldException noSuchFieldException)
    {
        return new ResponseEntity<String >("Entered valueType is not present is Database", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EmptyInputException.class)
    public ResponseEntity<String> handleEmptyInput(EmptyInputException emptyInputException)
    {
        return new ResponseEntity<String >("Some input fields are empty", HttpStatus.BAD_REQUEST);
    }

}
