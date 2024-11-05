package com.ssafy.edu.todo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.todo.model.Category;
import com.ssafy.edu.todo.requests.CategoryRequest;
import com.ssafy.edu.todo.service.CategoryService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService cs;

    @Autowired
    public CategoryController(CategoryService cs) {
        this.cs = cs;
    }

    @GetMapping("/list/{userSeq}")
    public ResponseEntity<?> getCategories(@PathVariable("userSeq") int userSeq) {
        try {
            Optional<List<Category>> categories=cs.getAllCategories(userSeq);
            if(categories.isPresent()){
                return new ResponseEntity<>(categories.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("카테고리 조회 실패", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }  

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getCategory(@PathVariable("id") int categoryId) {
        try {
            Optional<Category> category=cs.getCategoryById(categoryId);
            if(category.isPresent()){
                return new ResponseEntity<>(category.get(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>("카테고리 조회 실패", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        try {
            boolean isSuccess = cs.insertCategory(category);
            if (isSuccess) {
                return new ResponseEntity<>(category, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("카테고리 추가 실패", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") int id, @RequestBody CategoryRequest cr) {
        try {
            Category category = new Category();
            category.setId(id);
            category.setTitle(cr.getTitle());
            category.setColor(cr.getColor());
            boolean isSuccess = cs.updateCategory(category);
            if (isSuccess) {
                return new ResponseEntity<>(cr, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("카테고리 업데이트 실패", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") int id) {
        try {
            boolean isSuccess = cs.deleteCategory(id);
            if (isSuccess) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>("카테고리 삭제 실패", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
