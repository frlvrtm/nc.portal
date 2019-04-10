package com.nc.portal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class ErrorController {
    @ResponseStatus(value=  HttpStatus.FORBIDDEN)
    @ExceptionHandler(Exception.class)
    public String getError() {
        return "error/access-denied";
    }
}
