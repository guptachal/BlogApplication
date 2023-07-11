package com.portfolio.blog.Blog.service;

import com.portfolio.blog.Blog.payload.PostDto;
import com.portfolio.blog.Blog.payload.PostResponse;
import javafx.geometry.Pos;

import java.util.List;

public interface IPostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPost(int pageNo, int pageSize,String sortBy, String sortDir) throws Exception;
    List<PostDto> getPostByTitle(String title);
    PostDto updatePost(PostDto postDto, String title);
    public void deletePostByTitle(String title);
}
