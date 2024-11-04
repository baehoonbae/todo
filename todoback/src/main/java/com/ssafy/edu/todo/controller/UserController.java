package com.ssafy.edu.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.todo.model.User;
import com.ssafy.edu.todo.service.UserService;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {
    
    private final UserService us;

    @Autowired
    public UserController(UserService us) {
        this.us = us;
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody User user){
        try {
            boolean isS=us.join(user);
            if(isS){
                return new ResponseEntity<User>(user,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
