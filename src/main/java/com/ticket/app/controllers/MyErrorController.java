package com.ticket.app.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController  {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "404";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "404";
            }
            else if(statusCode == HttpStatus.FORBIDDEN.value()){
                return "404";
            }
            else if(statusCode == HttpStatus.BAD_REQUEST.value()){
                return "404";
            }
            else if(statusCode == HttpStatus.UNAUTHORIZED.value()){
                return "404";
            }
            else if(statusCode == HttpStatus.NOT_ACCEPTABLE.value()){
                return "404";
            }
            else if(statusCode == HttpStatus.REQUEST_TIMEOUT.value()){
                return "404";
            }
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}