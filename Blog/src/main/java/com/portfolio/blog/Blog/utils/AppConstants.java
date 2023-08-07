package com.portfolio.blog.Blog.utils;

public class AppConstants {
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "5";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "asc";
    public static final long EXPIRATION_TIME = 432_000_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String  JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String GET_ARRAYS_LLC = "Get Arrays, LLC";
    public static final String GET_ARRAYS_ADMIN = "User Management Portal";
    public static final String AUTHORITIES = "Authorities";
    public static final String FORBIDDEN = "You need to log in to access this page";
    public static final String ACCESS_DENIED = "You don't have the permission to access the page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    //public static final String[] PUBLIC_URLS = {"/user/login","/user/register",
    //        "/user/resetpassword/**","/user/image/**"};
    public static final String[] PUBLIC_URLS = {"/user/login","user/register"};

}