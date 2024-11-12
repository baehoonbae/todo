package com.ssafy.edu.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.edu.todo.mapper.CategoryMapper;
import com.ssafy.edu.todo.model.Category;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(CategoryMapper cm) {
        this.categoryMapper = cm;
    }

    @Override
    public Optional<List<Category>> getAllCategories(int userSeq) {
        List<Category> list = categoryMapper.selectAllCategoriesByUserSeq(userSeq);
        return Optional.ofNullable(list);
    }

    @Override
    public Optional<List<Category>> getAllCategories(String userId) {
        List<Category> list = categoryMapper.selectAllCategoriesByUserId(userId);
        return Optional.ofNullable(list);
    }

    @Override
    public Optional<Category> getCategoryById(int categoryId) {
        return Optional.ofNullable(categoryMapper.selectCategoryById(categoryId));
    }

    @Override
    public boolean insertCategory(Category category) {
        return categoryMapper.insert(category) > 0;
    }

    @Override
    public boolean updateCategory(Category category) {
        return categoryMapper.updateCategory(category) > 0;
    }

    @Override
    public boolean deleteCategory(int categoryId) {
        return categoryMapper.deleteByPrimaryKey(categoryId) > 0;
    }

}
