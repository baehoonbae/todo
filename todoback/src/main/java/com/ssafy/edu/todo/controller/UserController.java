package com.ssafy.edu.todo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.todo.model.User;
import com.ssafy.edu.todo.requests.LoginRequest;
import com.ssafy.edu.todo.responses.LoginResponse;
import com.ssafy.edu.todo.service.UserService;
import com.ssafy.edu.todo.util.JwtUtil;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {

    private final UserService us;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService us, JwtUtil jwtUtil) {
        this.us = us;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> join(@RequestBody User user) {
        try {
            boolean isS = us.join(user);
            if (isS) {
                return new ResponseEntity<User>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Optional<User> opUser = us.login(loginRequest);
            return opUser
                .map(user ->{
                    String token = jwtUtil.createToken(user.getUserId());
                    return ResponseEntity.ok(new LoginResponse(
                        token,
                        user.getUserId(),
                        "로그인 성공"));
                })
                .orElse(ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, null, "아이디 또는 비밀번호가 잘못되었습니다.")));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponse(null, null, "서버 오류 발생"));
        }
    }

}
