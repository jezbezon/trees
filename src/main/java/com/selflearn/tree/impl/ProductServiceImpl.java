package com.selflearn.tree.impl;

import com.selflearn.tree.datamapper.CategoryDTO;
import com.selflearn.tree.datamapper.ProductDTO;
import com.selflearn.tree.exceptions.CustomizeException;
import com.selflearn.tree.model.Category;
import com.selflearn.tree.model.Product;
import com.selflearn.tree.repository.CategoryRepository;
import com.selflearn.tree.repository.ProductRepository;
import com.selflearn.tree.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) {
        if(productRepository.findAll().stream().anyMatch(c->c.getName().equalsIgnoreCase(productDTO.getName()))){
            throw new CustomizeException.BadRequestException("This product name already exit","01");
        }
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(()-> new CustomizeException.NotFoundException("This Category not found!"));

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setBrand(productDTO.getBrand());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQty());
        product.setDescription(productDTO.getDescription());
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(long id) {
        return productRepository.findById(id).orElseThrow(() -> new CustomizeException.NotFoundException("This product not found","02"));
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product updateProduct(long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id).orElseThrow(() -> new CustomizeException.NotFoundException("This product not found","02"));
        product.setName(productDTO.getName());
        product.setBrand(productDTO.getBrand());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQty());
        product.setDescription(productDTO.getDescription());
        return productRepository.save(product);
    }

    @Override
    public List<Product> getByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getByNameWithBrand(String name, String brand) {
        return productRepository.findByNameWithBrand(name,brand);
    }

    @Override
    public List<Product> getByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getByBrandWithCategoryId(String brand, int categoryId) {
        return productRepository.findByBrandWithCategoryId(brand,categoryId);
    }

    @Override
    public List<Product> getByNameWithBrandWithCategoryId(String name, String brand, int categoryId) {
        return productRepository.findByNameWithBrandWithCategoryId(name,brand,categoryId);
    }

    @Override
    public List<Product> getByCategoryId(int categoryId) {
        return productRepository.findByCategory(categoryId);
    }
}
