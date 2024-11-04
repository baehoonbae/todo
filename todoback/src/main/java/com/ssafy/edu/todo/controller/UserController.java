package com.ssafy.edu.todo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.CrossOrigin;
=======
import org.springframework.web.bind.annotation.GetMapping;
>>>>>>> c92173fd09ca9be06398d6a78dc0ea76ff36346c
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.todo.model.User;
import com.ssafy.edu.todo.requests.LoginRequest;
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

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try {
            User user=new User();
            user.setUserId(loginRequest.getUserId());
            user.setUserPassword(loginRequest.getUserPassword());
            Optional<User> opUser=us.login(user);
            
            if(opUser.isPresent()){
                return new ResponseEntity<>(opUser.get(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
