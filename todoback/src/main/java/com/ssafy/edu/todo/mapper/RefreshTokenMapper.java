package com.ssafy.edu.todo.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.ssafy.edu.todo.model.RefreshToken;

@Mapper
public interface RefreshTokenMapper {
    void saveRefreshToken(RefreshToken refreshToken);
    RefreshToken findByUserId(String userId);
    void deleteByUserId(String userId);
    void updateRefreshToken(RefreshToken refreshToken);
} 