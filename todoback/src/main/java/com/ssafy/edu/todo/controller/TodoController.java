package com.ssafy.edu.todo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.todo.model.Todo;
import com.ssafy.edu.todo.service.TodoService;

@RestController
@RequestMapping("/api/todo")
public class TodoController {
    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService ts) {
        this.todoService = ts;
    }

    @GetMapping("/list/{userSeq}")
    public ResponseEntity<?> getTodos(@PathVariable("userSeq") int userSeq) {
        try {
            Optional<List<Todo>> todos = todoService.getAllTodoByUserSeq(userSeq);
            if (todos.isPresent()) {
                return new ResponseEntity<>(todos.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("할 일 조회 실패", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getTodo(@PathVariable("id") int categoryId) {
        try {
            Optional<Todo> todo = todoService.getTodoById(categoryId);
            if (todo.isPresent()) {
                return new ResponseEntity<>(todo.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("할 일 조회 실패", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addTodo(@RequestBody Todo todo) {
        try {
            boolean isSuccess = todoService.insertTodo(todo);
            if (isSuccess) {
                return new ResponseEntity<>(todo, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("할 일 추가 실패", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable("id") int id, @RequestBody Todo todo) {
        try {
            boolean isSuccess = todoService.updateTodo(todo);
            if (isSuccess) {
                return new ResponseEntity<>(todo, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("할 일 수정 실패", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/date/{date}")
    public ResponseEntity<?> updateTodoDate(@PathVariable("id")int id, @PathVariable("date") String date) {
        try {
            Todo todo = new Todo();
            todo.setId(id);
            todo.setDate(date);
            boolean isSuccess = todoService.updateTodo(todo);
            return new ResponseEntity<>(isSuccess ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/category/{categoryId}")
    public ResponseEntity<?> updateTodoCategory(@PathVariable("id")int id,@PathVariable("categoryId") int categoryId) {
        try {
            Todo todo = new Todo();
            todo.setId(id);
            todo.setCategoryId(categoryId);
            boolean isSuccess = todoService.updateTodo(todo);

            return new ResponseEntity<>(isSuccess ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") int id) {
        try {
            boolean isSuccess = todoService.deleteTodo(id);

            return new ResponseEntity<>(isSuccess ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
