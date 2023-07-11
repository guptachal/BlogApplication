package com.portfolio.blog.Blog.entity;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.action.internal.OrphanRemovalAction;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Post {

    @Id
    private String id;
    @Field(name = "title")
    @NotNull
    private String title;
    private String description;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @DBRef(db = "Comment")
    @Cascade(CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();
}
