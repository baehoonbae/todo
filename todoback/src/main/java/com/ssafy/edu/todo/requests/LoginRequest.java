package com.ssafy.edu.todo.requests;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginRequest {
    @NonNull private String userId;
    @NonNull private String userPassword;
}
