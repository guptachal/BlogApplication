package com.portfolio.blog.Blog.service;

import com.portfolio.blog.Blog.entity.User;
import com.portfolio.blog.Blog.exception.EmailExistException;
import com.portfolio.blog.Blog.exception.UserNotFoundException;
import com.portfolio.blog.Blog.exception.UsernameExistException;

import java.util.List;

public interface IUserDetailService {

    User register(String firstName, String lastName, String username, String email) throws UserNotFoundException, EmailExistException, UsernameExistException;

    User findByUsername(String username);
    User findUserByEmail(String email);

}
