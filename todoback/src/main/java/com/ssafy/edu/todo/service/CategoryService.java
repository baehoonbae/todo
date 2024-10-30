package com.ssafy.edu.todo.service;

import com.ssafy.edu.todo.model.Category;
import java.util.*;

public interface CategoryService {
    public Optional<List<Category>> getAllCategories(int userSeq);
    public Optional<Category> getCategoryById(int categoryId);
    public boolean insertCategory(Category category);
    public boolean updateCategory(Category category);
    public boolean deleteCategory(int categoryId);
}
