package com.ssafy.edu.todo.service;

import com.ssafy.edu.todo.responses.TokenResponse;

public interface TokenService {
    TokenResponse createTokens(String userId);
    TokenResponse refreshAccessToken(String refreshToken);
    void revokeRefreshToken(String userId);
    boolean validateRefreshToken(String refreshToken);
}
