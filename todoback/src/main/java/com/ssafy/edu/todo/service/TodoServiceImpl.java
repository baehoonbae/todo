package com.ssafy.edu.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.edu.todo.mapper.TodoMapper;
import com.ssafy.edu.todo.model.Todo;

@Service
@Transactional
public class TodoServiceImpl implements TodoService {

    private final TodoMapper todoMapper;

    @Autowired
    public TodoServiceImpl(TodoMapper todoMapper) {
        this.todoMapper = todoMapper;
    }

    @Override
    public Optional<List<Todo>> getAllTodoByCategoryId(int categoryId) {
        return Optional.ofNullable(todoMapper.selectTodoByCategoryId(categoryId));
    }

    @Override
    public Optional<Todo> getTodoById(int todoId) {
        return Optional.ofNullable(todoMapper.selectTodoById(todoId));
    }

    @Override
    public boolean insertTodo(Todo todo) {
        return todoMapper.insertSelective(todo)>0;
    }

    @Override
    public boolean deleteTodo(int todoId) {
        return todoMapper.deleteByPrimaryKey(todoId)>0;
    }

    @Override
    public boolean updateTodo(Todo todo) {
        return todoMapper.updateSelective(todo)>0;
    }

}
