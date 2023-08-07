package com.portfolio.blog.Blog.controller;

import com.portfolio.blog.Blog.payload.CommentDto;
import com.portfolio.blog.Blog.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    private ICommentService commentService;
    @Autowired
    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value="postId") String postId,
                                                    @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable(value = "postId") String postId){
        return new ResponseEntity<>(commentService.getAllComments(postId), HttpStatus.OK);
    }
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postId") String postId,
                                                     @PathVariable(value = "commentId") String commentId){
        System.out.println(postId);
        return new ResponseEntity<>(commentService.getCommentById(postId,commentId), HttpStatus.FOUND);
    }
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable(value = "postId") String postId,
                                                        @PathVariable(value = "commentId") String commentId,
                                                        @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.updateCommentById(postId,commentId,commentDto), HttpStatus.OK);
    }
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentById(@PathVariable(value = "postId") String postId,
                                                    @PathVariable(value = "commentId") String commentId){
        commentService.deleteCommentById(postId,commentId);
        return new ResponseEntity<>("Deleted Successfully!",HttpStatus.OK);
    }
}