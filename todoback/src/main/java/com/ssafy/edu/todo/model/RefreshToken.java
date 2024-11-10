package com.ssafy.edu.todo.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RefreshToken {
    private String userId;
    private String token;
    private LocalDateTime expiryDate;
} 
