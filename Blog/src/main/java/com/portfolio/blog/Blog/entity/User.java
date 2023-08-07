package com.portfolio.blog.Blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.util.Date;

@Document(collection = "User")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    private String id;
    @Indexed(unique = true)
    private String userId;
    private String firstName;
    private String lastName;
    @Indexed(unique = true)
    private String username;
    private String password;
    private String email;
    private String profileImageUrl;
    private Date lastLoginDate;
    private Date lastLoginDisplayDate;
    private Date joinDate;
    private String role;  //ROLE_ADMIN, ROLE_USER
    private String[] authorities; // create, read, update, delete
    private boolean isActive;
    private boolean isNotLocked;
}