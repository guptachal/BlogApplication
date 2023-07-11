package com.portfolio.blog.Blog.service.impl;

import com.portfolio.blog.Blog.entity.Comment;
import com.portfolio.blog.Blog.entity.Post;
import com.portfolio.blog.Blog.exception.BlogAPIException;
import com.portfolio.blog.Blog.exception.ResourceNotFoundException;
import com.portfolio.blog.Blog.payload.CommentDto;
import com.portfolio.blog.Blog.repository.ICommentRepository;
import com.portfolio.blog.Blog.repository.IPostRepository;
import com.portfolio.blog.Blog.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService implements ICommentService {
    private ICommentRepository commentRepository;
    private IPostRepository postRepository;

    @Autowired
    public CommentService(ICommentRepository commentRepository, IPostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }
    @Override
    public CommentDto createComment(String postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        //Setting up the creation time.
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        System.out.println("Post!");
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","id",postId));
        // set post to the comment entity.
        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        return mapToDto(comment);
    }
    @Override
    public List<CommentDto> getAllComments(String postId) {
        List<Comment> commentList = commentRepository.findByPostId(postId).orElseThrow(()-> new ResourceNotFoundException("Post","postId",postId));
        return commentList.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }
    @Override
    public CommentDto getCommentById(String postId, String commentId) {
        Post post = postRepository.getPostById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","commentId",commentId));
        System.out.println(post);
        System.out.println(comment);
        if(!comment.getPost().getId().equals(post.getId()))
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment cannot be found!");
        return mapToDto(comment);
    }
    @Override
    public CommentDto updateCommentById(String postId,String commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","postId",postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment","id",commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            System.out.println("Hi!");
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment doesn't exist!");
        }
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        comment.setUpdatedAt(LocalDateTime.now());
        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteCommentById(String postId, String commentId) {
        Post post = postRepository.getPostById(postId).
                orElseThrow(()->new ResourceNotFoundException("post","id",postId));
        Comment comment = commentRepository.findById(commentId).
                orElseThrow(()-> new ResourceNotFoundException("post","id", postId));
        if (!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment doesn't exist anymore!");
        }
        System.out.println("comment:"+comment);
        commentRepository.delete(comment);
    }
    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setBody(comment.getBody());
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setUpdatedAt(comment.getUpdatedAt());
        commentDto.setCreatedAt(comment.getCreatedAt());
        return commentDto;
    }
    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setBody(commentDto.getBody());
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setCreatedAt(commentDto.getCreatedAt());
        return comment;
    }
}