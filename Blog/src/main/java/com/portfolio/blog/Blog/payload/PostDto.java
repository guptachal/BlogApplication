package com.portfolio.blog.Blog.payload;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class PostDto {
    private String id;
    private int uniqueId;
    private String title;
    private String description;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<CommentDto> comments;
}
