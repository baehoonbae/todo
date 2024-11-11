package com.ssafy.edu.todo.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private Integer userSeq;
    private String userName;
    private String message;
} 