package com.selflearn.tree.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.selflearn.tree.datamapper.CategoryDTO;
import com.selflearn.tree.datamapper.ProductDTO;
import com.selflearn.tree.model.Category;
import com.selflearn.tree.model.Product;
import com.selflearn.tree.resposeClass.ResponseClass;
import com.selflearn.tree.service.CategoryService;
import com.selflearn.tree.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class TestProductController {

    private MockMvc mockMvc;

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void createProduct() throws Exception {
        Category category = Category.builder().id(1).name("Laptop").build();
        when(categoryService.createCategory(any(CategoryDTO.class))).thenReturn(category);
        Product product = new Product(1L,"Huawei a", new BigDecimal("999.98"),10,"Huawei","New feature", category);
        ResponseClass responseClass = new ResponseClass("00","Create product success", product);
        when(productService.createProduct(any(ProductDTO.class))).thenReturn(product);
        ObjectMapper objectMapper = new ObjectMapper();
        String result = objectMapper.writeValueAsString(responseClass);

        mockMvc.perform(post("/api/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product.productDTO(product))))
                .andExpect(status().isOk())
                .andExpect(content().json(result));
    }

    @Test
    void getByCategoryId() throws Exception {
        Category category = Category.builder().id(1).name("Laptop").build();
        when(categoryService.createCategory(any(CategoryDTO.class))).thenReturn(category);
        List<Product> product = List.of(new Product(1L,"Huawei a", new BigDecimal("999.98"),10,"Huawei","New feature", category));
        when(productService.getByCategoryId(1)).thenReturn(product);
         ResponseClass responseClass = new ResponseClass("00","fetch category success", product);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(get("/api/product/category/{id}",1))
                 .andExpect(content().json(objectMapper.writeValueAsString(responseClass)));
    }

    @Test
    void getByName() throws Exception{
        Category category = Category.builder().id(1).name("Laptop").build();
        when(categoryService.createCategory(any(CategoryDTO.class))).thenReturn(category);
        List<Product> product = List.of(new Product(1L,"Huawei a", new BigDecimal("999.98"),10,"Huawei","New feature", category));
        when(productService.getByName("HUAWEI")).thenReturn(product);
        ResponseClass responseClass = new ResponseClass("00","Fetch product success", product);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(get("/api/product/name").param("name","Huawei"))
                .andExpect(content().json(objectMapper.writeValueAsString(responseClass)));

    }

    @Test
    void getByBrandWithCategoryId() throws Exception{
        Category category = Category.builder().id(1).name("Laptop").build();
        when(categoryService.createCategory(any(CategoryDTO.class))).thenReturn(category);
        List<Product> product = List.of(new Product(1L,"Huawei a", new BigDecimal("999.98"),10,"Huawei","New feature", category));
        when(productService.getByBrandWithCategoryId("HUAWEI",1)).thenReturn(product);
        ResponseClass responseClass = new ResponseClass("00","Fetch product success", product);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(get("/api/product/brand-categoryId/{categoryId}",1)
                        .param("brand","Huawei"))
                .andExpect(content().json(objectMapper.writeValueAsString(responseClass)));

    }

    @Test
    void getByBrandWithCategoryIdIsEmpty() throws Exception{

        ObjectMapper objectMapper = new ObjectMapper();
        String message = "Fetch product success (No Product name with this search: Brand {Huawei}, CategoryId {1})";
        ResponseClass responseClass = new ResponseClass("00",message, new ArrayList<>());
        mockMvc.perform(get("/api/product/brand-categoryId/{categoryId}",1)
                        .param("brand","Huawei"))
                .andExpect(content().json(objectMapper.writeValueAsString(responseClass)));

    }

    @Test
    void getByNameWithBrandWithCategoryId() throws Exception{
        Category category = Category.builder().id(1).name("Laptop").build();
        when(categoryService.createCategory(any(CategoryDTO.class))).thenReturn(category);
        List<Product> product = List.of(new Product(1L,"Huawei a", new BigDecimal("999.98"),10,"Huawei","New feature", category));
        when(productService.getByNameWithBrandWithCategoryId("HUAWEI A","HUAWEI",1)).thenReturn(product);
        ResponseClass responseClass = new ResponseClass("00","Fetch product success", product);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(get("/api/product/name-brand-categoryId/{categoryId}",1)
                        .param("name","Huawei a")
                        .param("brand","Huawei"))
                .andExpect(content().json(objectMapper.writeValueAsString(responseClass)));

    }

    @Test
    void getByNameWithBrandWithCategoryIdIsEmpty() throws Exception{
        Category category = Category.builder().id(1).name("Laptop").build();
        when(categoryService.createCategory(any(CategoryDTO.class))).thenReturn(category);
        List<Product> product = List.of(new Product(1L,"Huawei a", new BigDecimal("999.98"),10,"Huawei","New feature", category));
        when(productService.getByNameWithBrandWithCategoryId("huawei a","huawei",1)).thenReturn(product);
        String message = "Fetch product success (No Product name with this search: Product {Huawei a} ,Brand {Huawei}, CategoryId {1})";
        ResponseClass responseClass = new ResponseClass("00",message, new ArrayList<>());
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(get("/api/product/name-brand-categoryId/{categoryId}",1)
                        .param("name","Huawei a")
                        .param("brand","Huawei"))
                .andExpect(content().json(objectMapper.writeValueAsString(responseClass)));

    }

}
