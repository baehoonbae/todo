package com.ssafy.edu.todo.controller;

import java.util.Locale.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.todo.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService cs;

    @Autowired
    public CategoryController(CategoryService cs) {
        this.cs=cs;
    }
    
    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategory(@PathVariable("categoryId") int categoryId){
        try {
            return new ResponseEntity<Category>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);        
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody Category category){
        try{
            
            return new ResponseEntity<>(category,HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
