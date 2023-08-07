package com.portfolio.blog.Blog.exception;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.portfolio.blog.Blog.entity.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class ExceptionHandling {
    private static final Logger LOGGER  = LoggerFactory.getLogger(ExceptionHandling.class);
    public static final String  ACCOUNT_LOCKED = "Your account have been locked";
    public static final String INTERNAL_SERVER_ERROR = "An error occurred while processing your request";
    public static final String INCORRECT_CREDENTIALS = "Username/Password mismatch.";
    public static final String ACCOUNT_DISABLED = "Your account have been disabled";
    public static final String NOT_ENOUGH_PERMISSION = "You don't have the permission to access the resource";

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<HttpResponse> accountDisableException(){
        return createHttpResponse(BAD_REQUEST, ACCOUNT_DISABLED);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpResponse> badCredentialException(){
        return createHttpResponse(BAD_REQUEST,INCORRECT_CREDENTIALS);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpResponse> accessDeniedException(){
        return createHttpResponse(FORBIDDEN,NOT_ENOUGH_PERMISSION);
    }
    @ExceptionHandler(LockedException.class)
    public ResponseEntity<HttpResponse> accountLockedException(){
        return createHttpResponse(UNAUTHORIZED,ACCOUNT_LOCKED);
    }
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<HttpResponse> tokenExpiredException(TokenExpiredException exception){
        return createHttpResponse(UNAUTHORIZED,exception.getMessage().toUpperCase());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> internalServerException(){
        return createHttpResponse(UNAUTHORIZED,INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message){
        HttpResponse httpResponse = new HttpResponse(httpStatus.value(),httpStatus,
                httpStatus.getReasonPhrase().toUpperCase(),message.toUpperCase());
        LOGGER.error(message);
        return new ResponseEntity<>(httpResponse,httpStatus);
    }
}
