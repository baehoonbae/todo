package com.ssafy.edu.todo.service;

import java.util.List;
import java.util.Optional;

import com.ssafy.edu.todo.model.Todo;

public interface TodoService {
    public Optional<List<Todo>> getAllTodoByCategoryId(int categoryId);
    public Optional<Todo> getTodoById(int todoId);
    public int insertTodo(Todo todo);
    public int updateTodo(Todo todo);
    public int deleteTodo(int todoId);
}
