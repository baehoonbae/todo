package com.ssafy.edu.todo.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.ssafy.edu.todo.exception.InvalidTokenException;
import com.ssafy.edu.todo.mapper.RefreshTokenMapper;
import com.ssafy.edu.todo.model.RefreshToken;
import com.ssafy.edu.todo.responses.TokenResponse;
import com.ssafy.edu.todo.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenMapper refreshTokenMapper;

    @Override
    public TokenResponse createTokens(String userId) {
        String accessToken = jwtUtil.createAccessToken(userId);
        String refreshToken = jwtUtil.createRefreshToken(userId);
        
        saveOrUpdateRefreshToken(userId, refreshToken);
        
        return new TokenResponse(accessToken, refreshToken);
    }

    private void saveOrUpdateRefreshToken(String userId, String refreshToken) {
        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setUserId(userId);
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setExpiryDate(LocalDateTime.now().plusDays(14));
        
        RefreshToken existingToken = refreshTokenMapper.findByUserId(userId);
        if (existingToken != null) {
            refreshTokenMapper.updateRefreshToken(refreshTokenEntity);
        } else {
            refreshTokenMapper.saveRefreshToken(refreshTokenEntity);
        }
    }

    @Override
    public TokenResponse refreshAccessToken(String refreshToken) {
        validateRefreshTokenOrThrow(refreshToken);
        String userId = jwtUtil.getUserId(refreshToken);
        String newAccessToken = jwtUtil.createAccessToken(userId);
        
        return new TokenResponse(newAccessToken, refreshToken);
    }

    private void validateRefreshTokenOrThrow(String refreshToken) {
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new InvalidTokenException("유효하지 않은 리프레시 토큰");
        }

        String userId = jwtUtil.getUserId(refreshToken);
        RefreshToken savedToken = refreshTokenMapper.findByUserId(userId);

        if (savedToken == null || !savedToken.getToken().equals(refreshToken)) {
            throw new InvalidTokenException("저장된 리프레시 토큰과 불일치");
        }

        if (savedToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            refreshTokenMapper.deleteByUserId(userId);
            throw new InvalidTokenException("만료된 리프레시 토큰");
        }
    }

    @Override
    public void revokeRefreshToken(String userId) {
        refreshTokenMapper.deleteByUserId(userId);
    }

    @Override
    public boolean validateRefreshToken(String refreshToken) {
        try {
            validateRefreshTokenOrThrow(refreshToken);
            return true;
        } catch (InvalidTokenException e) {
            return false;
        }
    }
}
