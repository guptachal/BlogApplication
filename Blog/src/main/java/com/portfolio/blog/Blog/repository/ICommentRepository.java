package com.portfolio.blog.Blog.repository;

import com.portfolio.blog.Blog.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ICommentRepository extends MongoRepository<Comment, String> {
    Optional<List<Comment>> findByPostId(String postId);
    Optional<Comment> findById(String commentId);
}
