package com.portfolio.blog.Blog.controller;

import com.portfolio.blog.Blog.domain.UserPrincipal;
import com.portfolio.blog.Blog.entity.User;
import com.portfolio.blog.Blog.exception.EmailExistException;
import com.portfolio.blog.Blog.exception.ExceptionHandling;
import com.portfolio.blog.Blog.exception.UserNotFoundException;
import com.portfolio.blog.Blog.exception.UsernameExistException;
import com.portfolio.blog.Blog.security.JwtTokenProvider;
import com.portfolio.blog.Blog.service.IUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static com.portfolio.blog.Blog.utils.AppConstants.JWT_TOKEN_HEADER;

@RestController
@RequestMapping(value = "/user")
public class AuthController extends ExceptionHandling {
    private IUserDetailService userDetailsService;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    public AuthController(IUserDetailService userDetailsService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/home")
    public String showUser(){
        return "application works!";
    }
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws UserNotFoundException, EmailExistException, UsernameExistException {
        User newUser =  userDetailsService.register(user.getFirstName(),
                user.getLastName(),user.getUsername(), user.getEmail());
        return new ResponseEntity<>(newUser,HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user){
        authenticate(user.getUsername(),user.getPassword());
        User loginUser = userDetailsService.findByUsername(user.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeaders = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(loginUser,jwtHeaders,HttpStatus.OK);
    }
    @PostMapping

    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(userPrincipal));
        return headers;
    }


    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
    }
}