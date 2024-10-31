package com.ssafy.edu.todo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.edu.todo.mapper.UserMapper;
import com.ssafy.edu.todo.model.User;
import com.ssafy.edu.todo.requests.LoginRequest;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper um;

    @Autowired
    public UserServiceImpl(UserMapper um) {
        this.um = um;
    }

    @Override
    public Optional<User> login(LoginRequest loginRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    @Override
    public boolean join(User user) {
        return um.insertSelective(user)>0;
    }

}
