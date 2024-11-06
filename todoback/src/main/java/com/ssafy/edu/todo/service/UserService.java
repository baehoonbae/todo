package com.ssafy.edu.todo.service;

import java.util.Optional;

import com.ssafy.edu.todo.model.User;
import com.ssafy.edu.todo.requests.LoginRequest;

public interface UserService {
    public Optional<User> login(LoginRequest loginRequest);
    public boolean join(User user);
}
