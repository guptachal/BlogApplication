package com.portfolio.blog.Blog.service.impl;

import com.portfolio.blog.Blog.entity.User;
import com.portfolio.blog.Blog.domain.UserPrincipal;
import com.portfolio.blog.Blog.exception.EmailExistException;
import com.portfolio.blog.Blog.exception.ResourceNotFoundException;
import com.portfolio.blog.Blog.exception.UserNotFoundException;
import com.portfolio.blog.Blog.exception.UsernameExistException;
import com.portfolio.blog.Blog.repository.IUserDetailsRepository;
import com.portfolio.blog.Blog.service.IUserDetailService;
import com.portfolio.blog.Blog.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@Qualifier("UserDetailService")
public class UserDetailsServiceImpl implements UserDetailsService, IUserDetailService {
    private IUserDetailsRepository userDetailsRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public UserDetailsServiceImpl(IUserDetailsRepository userDetailsRepository,
                                  BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsRepository = userDetailsRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userDetailsRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException());
        return new UserPrincipal(user);
    }
    public User registerUser(User user) {
        return null;
    }

    @Override
    public User register(String firstName, String lastName, String username, String email) throws UserNotFoundException, EmailExistException, UsernameExistException {
        //validateNewUsernameAndEmail(null,username,email);
        User user = new User();
        user.setUserId(genarateUserId());
        String password = generatePassword();
        String encodedPassword = encodePassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setJoinDate(new Date());
        user.setPassword(encodedPassword);
        user.setActive(true);
        user.setNotLocked(true);
        user.setRole(Roles.ROLE_USER.name());
        user.setAuthorities(Roles.ROLE_USER.getAuthorities());
        return userDetailsRepository.save(user);
    }

    private String encodePassword(String password) {
        return "$2a$12$UFv2oGVGGJeq6kyvPnx3B.YjsVLwr.VR64BPrTRzSItPnKOlox7GW";
       // return bCryptPasswordEncoder.encode(password);
    }

    private String generatePassword() {
        return "admin";
    }

    private String genarateUserId() {
        Date date = new Date();
        String random = date.toString().trim();
        return random;
    }

    private User validateNewUsernameAndEmail(String currentUserName, String newUserName, String newEmail) throws UserNotFoundException, UsernameExistException, EmailExistException {
        if(!currentUserName.isEmpty()){
            User currentUser = findByUsername(currentUserName);
            if(currentUser == null){
                throw new UserNotFoundException("User not fond with the username: "+ currentUserName);
            }
            User userByUserName = findByUsername(currentUserName);
            if(userByUserName!=null && !currentUser.getId().equals(userByUserName.getId())){
                throw new UsernameExistException("Username already exist");
            }
            User userByEmail = findUserByEmail(newEmail);
            if(userByEmail!=null && !currentUser.getId().equals(userByEmail.getId())){
                throw new EmailExistException("Email already exist");
            }
            return currentUser;
        }
        else {
            User userByUsername = findByUsername(newUserName);
            if(userByUsername!=null){
                throw new UsernameExistException("Username exist");
            }
            User userByEmail = findByUsername(newUserName);
            if(userByEmail!=null){
                throw new EmailExistException("Email already exist");
            }
            return null;
        }
    }
    @Override
    public User findByUsername(String username) {
        return userDetailsRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("username",username,"abs"));
    }

    @Override
    public User findUserByEmail(String email) {
        return null;
    }
}