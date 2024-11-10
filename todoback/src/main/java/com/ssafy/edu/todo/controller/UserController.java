package com.ssafy.edu.todo.controller;

import java.util.Optional;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.todo.exception.InvalidTokenException;
import com.ssafy.edu.todo.model.User;
import com.ssafy.edu.todo.requests.LoginRequest;
import com.ssafy.edu.todo.requests.RefreshTokenRequest;
import com.ssafy.edu.todo.responses.LoginResponse;
import com.ssafy.edu.todo.responses.TokenResponse;
import com.ssafy.edu.todo.service.TokenService;
import com.ssafy.edu.todo.service.UserService;
import com.ssafy.edu.todo.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, TokenService tokenService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.tokenService = tokenService;
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
        try {
            Optional<User> opUser = userService.login(loginRequest);
            return opUser
                    .map(user -> {
                        TokenResponse tokens = tokenService.createTokens(user.getUserId());

                        ResponseCookie refreshTokenCookie = ResponseCookie
                                .from("refreshToken", tokens.getRefreshToken())
                                .httpOnly(true)         // 자바스크립트 접근 불가
                                .secure(true)             // https 프로토콜에서만 전송(개발 단계에서는 주석 처리)
                                .path("/")                  // 모든 경로에서 접근 가능
                                .maxAge(60 * 60 * 24 * 14)       // 2주
                                .sameSite("Strict")     // CSRF 방지
                                .domain("localhost")      // 도메인 설정
                                .build();

                        return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                                .body(new LoginResponse(
                                        tokens.getAccessToken(),
                                        user.getUserId(),
                                        user.getUserName(),
                                        "로그인 성공!"));
                    })
                    .orElse(ResponseEntity
                            .status(HttpStatus.UNAUTHORIZED)
                            .body(new LoginResponse(null, null, null, "로그인 실패")));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponse(null, null, null, "서버 오류"));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        try {
            if (request.getRefreshToken() == null || request.getRefreshToken().isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("리프레시 토큰이 필요합니다.");
            }

            TokenResponse tokens = tokenService.refreshAccessToken(request.getRefreshToken());
            return ResponseEntity.ok(tokens);
        } catch (InvalidTokenException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("토큰 갱신 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String bearerToken) {
        try {
            String token = bearerToken.substring(7); // "Bearer " 제거
            String userId = jwtUtil.getUserId(token);
            tokenService.revokeRefreshToken(userId);
            return ResponseEntity.ok().body("로그아웃 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("로그아웃 실패");
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

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserInfo(@PathVariable String userId) {
        try{
            Optional<User> opUser = userService.selectUserByUserId(userId);
            return opUser
                    .map(user -> ResponseEntity.ok(user))
                    .orElseThrow(() -> new NotFoundException("유저 정보를 찾을 수 없습니다."));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }   

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUserInfo(@PathVariable String userId, @RequestBody User user) {
        try {
            boolean success=userService.updateUserInfo(userId, user);
            if(success){
                return ResponseEntity.ok().body("유저 정보 수정 성공");
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유저 정보 수정 실패");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }
}
