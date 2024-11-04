package com.ssafy.edu.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.edu.todo.mapper.CategoryMapper;
import com.ssafy.edu.todo.model.Category;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper cm;

    @Autowired
    public CategoryServiceImpl(CategoryMapper cm) {
        this.cm = cm;
    }

    @Override
    public Optional<List<Category>> getAllCategories(int userSeq) {
        List<Category> list = cm.selectAllCategoriesByUserSeq(userSeq);
        return Optional.ofNullable(list);
    }

    @Override
    public Optional<Category> getCategoryById(int categoryId) {
        return Optional.ofNullable(cm.selectCategoryById(categoryId));
    }

    @Override
    public boolean insertCategory(Category category) {
        return cm.insert(category) > 0;
    }

    @Override
    public boolean updateCategory(Category category) {
        return cm.updateCategory(category) > 0;
    }

    @Override
    public boolean deleteCategory(int categoryId) {
        return cm.deleteByPrimaryKey(categoryId) > 0;
    }

}
