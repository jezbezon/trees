package com.selflearn.tree.service;

import com.selflearn.tree.datamapper.ProductDTO;
import com.selflearn.tree.model.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(ProductDTO productDTO);

    Product getProductById(long id);

    List<Product> getAllProduct();

    void deleteById(long id);

    Product updateProduct(long id, ProductDTO productDTO);

    List<Product> getByBrand(String brand);

    List<Product> getByNameWithBrand(String name, String brand);

    List<Product> getByName(String name);

    List<Product> getByBrandWithCategoryId(String brand, int categoryId);

    List<Product> getByNameWithBrandWithCategoryId(String name, String brand, int categoryId);

    List<Product> getByCategoryId(int categoryId);
}
