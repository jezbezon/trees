package com.selflearn.tree.controller;

import com.selflearn.tree.datamapper.ProductDTO;
import com.selflearn.tree.resposeClass.ResponseClass;
import com.selflearn.tree.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    public ResponseClass createProduct(@RequestBody ProductDTO productDTO){
        return new ResponseClass("00","Create product success", productService.createProduct(productDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseClass updateProduct(@RequestBody ProductDTO productDTO, @PathVariable(value = "id") long id){
        return new ResponseClass("00","update product success", productService.updateProduct(id,productDTO));
    }

    @GetMapping("/brand")
    public ResponseClass getByBrand(@RequestParam(value = "brand") String brand){
        if(productService.getByBrand(brand).isEmpty()){
            return new ResponseClass("00","Fetch product success(No brand with this search {"+brand+"})",  new ArrayList<>());
        }
        return new ResponseClass("00","Fetch product success", productService.getByBrand(brand));
    }

    @GetMapping("/NameWithBrand")
    public ResponseClass getByNameWithBrand(@RequestParam(value = "name") String name, @RequestParam(value = "brand") String brand){
        if(productService.getByNameWithBrand(name.toUpperCase(),brand.toUpperCase()).isEmpty()){
            String message = String.format("Fetch product success (No Product name and brand with this search: Product {%s} Brand {%s})", name, brand);
            return new ResponseClass("00",message,  new ArrayList<>());
        }
        return new ResponseClass("00","Fetch product success", productService.getByNameWithBrand(name.toUpperCase(),brand.toUpperCase()));
    }

    @GetMapping("/name")
    public ResponseClass getByName(@RequestParam(value = "name") String name){
        if(productService.getByName(name.toUpperCase()).isEmpty()){
            String message = String.format("Fetch product success (No Product name with this search: Product {%s})", name);
            return new ResponseClass("00",message,  new ArrayList<>());
        }
        return new ResponseClass("00","Fetch product success", productService.getByName(name.toUpperCase()));
    }

    @GetMapping("/brand-categoryId/{categoryId}")
    public ResponseClass getByBrandWithCategoryId(@RequestParam(value = "brand") String brand, @PathVariable(value = "categoryId") int categoryId){
        if(productService.getByBrandWithCategoryId(brand.toUpperCase(),categoryId).isEmpty()){
            String message = String.format("Fetch product success (No Product name with this search: Brand {%s}, CategoryId {%s})", brand, categoryId);
            return new ResponseClass("00",message, new ArrayList<>());
        }
        return new ResponseClass("00","Fetch product success", productService.getByBrandWithCategoryId(brand.toUpperCase(),categoryId));
    }

    @GetMapping("/name-brand-categoryId/{categoryId}")
    public ResponseClass getByNameWithBrandWithCategoryId(@RequestParam(value = "name") String name,@RequestParam(value = "brand") String brand, @PathVariable(value = "categoryId") int categoryId){
        if(productService.getByNameWithBrandWithCategoryId(name.toUpperCase(),brand.toUpperCase(),categoryId).isEmpty()){
            String message = String.format("Fetch product success (No Product name with this search: Product {%s} ,Brand {%s}, CategoryId {%s})", name, brand, categoryId);
            return new ResponseClass("00",message, new ArrayList<>());
        }
        return new ResponseClass("00","Fetch product success", productService.getByNameWithBrandWithCategoryId(name.toUpperCase(),brand.toUpperCase(),categoryId));
    }

    @GetMapping("/category/{id}")
    public ResponseClass getByCategoryId (@PathVariable(value = "id") int id){
        return new ResponseClass("00","fetch category success", productService.getByCategoryId(id));
    }

}
