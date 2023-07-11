package com.portfolio.blog.Blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comment")
public class Comment {
    @Id
    private String id;
    private String name;
    private String email;
    private String body;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @DBRef
    private Post post;
}