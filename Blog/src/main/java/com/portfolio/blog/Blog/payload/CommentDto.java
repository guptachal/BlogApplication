package com.portfolio.blog.Blog.payload;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private String id;
    private String name;
    private String email;
    private String body;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
