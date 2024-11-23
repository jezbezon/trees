package com.selflearn.tree.impl;

import com.selflearn.tree.datamapper.CategoryDTO;
import com.selflearn.tree.exceptions.CustomizeException;
import com.selflearn.tree.model.Category;
import com.selflearn.tree.repository.CategoryRepository;
import com.selflearn.tree.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        if(categoryRepository.findAll().stream().anyMatch(c-> c.getName().equalsIgnoreCase(categoryDTO.name()))){
            throw new CustomizeException.BadRequestException("This category already exit!");
        }
        Category category = Category.builder().name(categoryDTO.name()).build();
        return categoryRepository.save(category);

    }

    @Override
    public List<Category> getCategoryList() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CustomizeException.NotFoundException("Customer id:"+id+"not found"));
    }

    @Override
    public void deleteCategoryById(int id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category updateCategoryById(int id, CategoryDTO categoryDTO) {
        if(categoryRepository.findAll().stream().anyMatch(c-> c.getName().equalsIgnoreCase(categoryDTO.name()))){
            throw new CustomizeException.BadRequestException("This category name already exit!!");
        }
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CustomizeException.NotFoundException("Customer id:"+id+"not found"));
        category.setName(categoryDTO.name());
        return categoryRepository.save(category);
    }
}
