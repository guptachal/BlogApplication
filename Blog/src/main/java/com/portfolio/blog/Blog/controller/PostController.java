package com.portfolio.blog.Blog.controller;

import com.portfolio.blog.Blog.payload.PostDto;
import com.portfolio.blog.Blog.payload.PostResponse;
import com.portfolio.blog.Blog.service.IPostService;
import com.portfolio.blog.Blog.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    private IPostService postService;
    @Autowired
    public PostController(IPostService postService) {
        this.postService = postService;
    }

    // Create the bolg post Rest API
    @PostMapping("/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    @GetMapping("/posts")
    public PostResponse getAllPost(
            @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false) String sortDir
    ) throws Exception{
        return postService.getAllPost(pageNo,pageSize,sortBy,sortDir);
    }
    @GetMapping("/posts/{title}")
    public ResponseEntity<List<PostDto>> getPostByTitle(@PathVariable String title){
        return ResponseEntity.ok(postService.getPostByTitle(title));
    }
    @PutMapping("/posts/{title}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable(name="title") String title){
        PostDto postResponse = postService.updatePost(postDto, title);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }
    @DeleteMapping("/posts/{title}")
    public ResponseEntity<String> deletePosts(@PathVariable(name = "title") String title){
        postService.deletePostByTitle(title);
        return new ResponseEntity<>("Deleted Successfully!", HttpStatus.OK);
    }
}