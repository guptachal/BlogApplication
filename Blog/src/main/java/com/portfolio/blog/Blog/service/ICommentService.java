package com.portfolio.blog.Blog.service;

import com.portfolio.blog.Blog.payload.CommentDto;

import java.util.List;

public interface ICommentService {
    CommentDto createComment(String postId, CommentDto commentDto);
    List<CommentDto> getAllComments(String postId);
    CommentDto getCommentById(String postId, String commentId);
    CommentDto updateCommentById(String postId,String commentId, CommentDto commentDto);
    public void deleteCommentById(String postId, String commentId);
}
