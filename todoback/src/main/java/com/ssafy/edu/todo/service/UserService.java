package com.ssafy.edu.todo.service;

import java.util.Optional;

import com.ssafy.edu.todo.model.User;

public interface UserService {
    public Optional<User> login(User user);
    public boolean join(User user);
}
