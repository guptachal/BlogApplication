package com.portfolio.blog.Blog.exception;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public BlogAPIException(String message,HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }
    public BlogAPIException(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
