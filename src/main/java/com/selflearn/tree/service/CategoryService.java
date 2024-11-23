package com.selflearn.tree.service;

import com.selflearn.tree.datamapper.CategoryDTO;
import com.selflearn.tree.model.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(CategoryDTO categoryDTO);

    List<Category> getCategoryList();

    Category getCategoryById(int id);

    void deleteCategoryById(int id);

    Category updateCategoryById(int id, CategoryDTO categoryDTO);
}
