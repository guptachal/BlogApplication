package com.portfolio.blog.Blog.utils;

public enum Roles {
    ROLE_USER  (Authority.USER_AUTHORITIES),
    ROLE_HR  (Authority.HR_AUTHORITIES),
    ROLE_MANAGER  (Authority.MANAGER_AUTHORITIES),
    ROLE_ADMIN  (Authority.ADMIN_AUTHORITIES),
    ROLE_SUPER_USER  (Authority.SUPER_USER);


    private String[] authorities;

    Roles(String... authorities) {
        this.authorities = authorities;
    }
    public String[] getAuthorities(){
        return this.authorities;
    }
}
