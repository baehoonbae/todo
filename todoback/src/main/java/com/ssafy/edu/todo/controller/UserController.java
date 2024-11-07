package com.ssafy.edu.todo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.todo.model.User;
import com.ssafy.edu.todo.requests.LoginRequest;
import com.ssafy.edu.todo.responses.LoginResponse;
import com.ssafy.edu.todo.service.UserService;
import com.ssafy.edu.todo.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> join(@RequestBody User user) {
        log.debug("회원가입 시작");
        try {
            boolean isS = userService.join(user);
            if (isS) {
                // log.debug("회원가입 성공");
                return new ResponseEntity<User>(user, HttpStatus.CREATED);
            } else {
                // log.error("회원가입 실패");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            // log.error("회원가입 서버오류");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // log.debug("로그인 시작");
        try {
            Optional<User> opUser = userService.login(loginRequest);
            return opUser
                    .map(user -> {
                        String token = jwtUtil.createToken(user.getUserId());
                        return ResponseEntity.ok(new LoginResponse(token, user.getUserId(), "로그인 성공"));
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

    @GetMapping("/check-username/{userName}")
    public ResponseEntity<?> checkUserNameDuplicated(@PathVariable String userName) {
        try {
            Optional<User> opUser = userService.selectUserByUserName(userName);
            return opUser
                    .map(user -> {
                        return ResponseEntity.status(HttpStatus.CONFLICT).body("사용할 수 없는 닉네임입니다.");
                    })
                    .orElse(ResponseEntity.status(HttpStatus.OK).body("사용 가능한 닉네임입니다."));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }

    @GetMapping("/check-userid/{userId}")
    public ResponseEntity<?> checkUserIdDuplicated(@PathVariable String userId) {
        try {
            Optional<User> opUser = userService.selectUserByUserId(userId);
            return opUser
                    .map(user -> {
                        return ResponseEntity.status(HttpStatus.CONFLICT).body("사용할 수 없는 아이디입니다.");
                    })
                    .orElse(ResponseEntity.status(HttpStatus.OK).body("사용 가능한 아이디입니다."));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }

}
