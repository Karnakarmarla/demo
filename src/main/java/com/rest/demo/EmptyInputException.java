package com.rest.demo;

import java.util.Date;

public class EmptyInputException extends RuntimeException{
    private String errorcode;
    private String errormessage;

    public EmptyInputException(String errorcode, String errormessage) {
        this.errorcode = errorcode;
        this.errormessage = errormessage;
    }
}
