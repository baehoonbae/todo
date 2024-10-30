package com.ssafy.edu.todo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ssafy.edu.todo.model.User;
import com.ssafy.edu.todo.requests.LoginRequest;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public Optional<User> login(LoginRequest loginRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    @Override
    public int join(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'join'");
    }

}
