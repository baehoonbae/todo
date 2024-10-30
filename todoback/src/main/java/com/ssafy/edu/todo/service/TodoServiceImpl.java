package com.ssafy.edu.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ssafy.edu.todo.model.Todo;

@Service
public class TodoServiceImpl implements TodoService {

    @Override
    public Optional<List<Todo>> getAllTodoByCategoryId(int categoryId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllTodoByCategoryId'");
    }

    @Override
    public Optional<Todo> getTodoById(int todoId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTodoById'");
    }

    @Override
    public int insertTodo(Todo todo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertTodo'");
    }

    @Override
    public int updateTodo(Todo todo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTodo'");
    }

    @Override
    public int deleteTodo(int todoId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteTodo'");
    }

}
