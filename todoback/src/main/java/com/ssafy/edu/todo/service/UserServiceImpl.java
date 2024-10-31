package com.ssafy.edu.todo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.edu.todo.mapper.UserMapper;
import com.ssafy.edu.todo.model.User;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper um;

    @Autowired
    public UserServiceImpl(UserMapper um) {
        this.um = um;
    }

    @Override
    public Optional<User> login(User user) {
        return Optional.ofNullable(um.selectUserByUserIdAndUserPassword(user));
    }

    @Override
    public boolean join(User user) {
        return um.insertSelective(user)>0;
    }

}
