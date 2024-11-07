package com.ssafy.edu.todo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.edu.todo.mapper.UserMapper;
import com.ssafy.edu.todo.model.User;
import com.ssafy.edu.todo.requests.LoginRequest;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserMapper um, PasswordEncoder passwordEncoder) {
        this.userMapper = um;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> login(LoginRequest loginRequest) {
        User user = userMapper.selectUserByUserId(loginRequest.getUserId());
        if(user!=null){
            log.info("user: {}", user);
            if(user.getUserPassword().equals(loginRequest.getUserPassword())){
                String encodedPassword = passwordEncoder.encode(loginRequest.getUserPassword());
                user.setUserPassword(encodedPassword);
                userMapper.updateByPrimaryKey(user);
                return Optional.of(user);
            }else if(passwordEncoder.matches(loginRequest.getUserPassword(), user.getUserPassword())){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean join(User user) {
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        return userMapper.insertSelective(user) > 0;
    }

    @Override
    public Optional<User> selectUserByUserName(String userName) {
        return Optional.ofNullable(userMapper.selectUserByUserName(userName));
    }

    @Override
    public Optional<User> selectUserByUserId(String userId) {
        return Optional.ofNullable(userMapper.selectUserByUserId(userId));
    }

}
