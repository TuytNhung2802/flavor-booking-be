package com.flavorbooking.dtos.response;

import org.springframework.http.HttpStatus;

public class ResponseMessage {


    private  String message;
    private  Integer status;

    public ResponseMessage(String s, HttpStatus badRequest) {
    }

    public ResponseMessage( String message, Integer status) {

        this.message = message;
        this.status = status;
    }


    public Integer getStatusCode() {
        return status;
    }

    public void setStatusCode(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
