package com.ssafy.edu.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ssafy.edu.todo.model.Category;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Override
    public Optional<List<Category>> getAllCategories(int userSeq) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllCategories'");
    }

    @Override
    public Optional<Category> getCategoryById(int categoryId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCategoryById'");
    }

    @Override
    public int insertCategory(Category category) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertCategory'");
    }

    @Override
    public int updateCategory(Category category) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCategory'");
    }

    @Override
    public int deleteCategory(int categoryId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteCategory'");
    }

}
