package com.selflearn.tree.controller;

import com.selflearn.tree.datamapper.CategoryDTO;
import com.selflearn.tree.model.Category;
import com.selflearn.tree.resposeClass.ResponseClass;
import com.selflearn.tree.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/create")
    public ResponseClass createCategory(@RequestBody CategoryDTO categoryDTO) {
        return new ResponseClass("00", "create category success", categoryService.createCategory(categoryDTO));
    }

    @GetMapping({"id"})
    public ResponseClass getCategoryById(@PathVariable int id){
        return new ResponseClass("00", "Create category success", categoryService.getCategoryById(id));
    }

    @GetMapping
    public ResponseClass getCategoryList(){
        return new ResponseClass("00", "Get all category success", categoryService.getCategoryList());
    }

    @PatchMapping("update/{id}")
    public ResponseClass updateCategory(@PathVariable int id, @RequestBody CategoryDTO categoryDTO){
        return new ResponseClass("00", "Update category success", categoryService.updateCategoryById(id,categoryDTO));
    }

    @DeleteMapping("delete/{id}")
    public ResponseClass deleteCategory(@PathVariable int id){
        Category category= categoryService.getCategoryById(id);
        categoryService.deleteCategoryById(id);
        return new ResponseClass("00", "Delete category success", category);
    }

}
