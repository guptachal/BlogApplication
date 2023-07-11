package com.portfolio.blog.Blog.service;

import com.portfolio.blog.Blog.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IUserDetailsService extends MongoRepository<User, String> {
}
