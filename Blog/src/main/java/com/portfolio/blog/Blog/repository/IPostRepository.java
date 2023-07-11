package com.portfolio.blog.Blog.repository;

import com.portfolio.blog.Blog.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface IPostRepository extends MongoRepository<Post, String> {
    Optional<List<Post>> getPostByTitle(String id);
    Optional<Post> getPostById(String id);
}
