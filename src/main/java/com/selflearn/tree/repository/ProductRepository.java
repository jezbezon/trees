package com.selflearn.tree.repository;

import com.selflearn.tree.model.Product;
import com.selflearn.tree.service.ProductService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.brand like %:brand% ")
    public List<Product> findByBrand(@Param("brand") String brand);

    @Query("select p from Product p where upper(p.name) like %:name% and upper(p.brand) like %:brand%")
    public List<Product> findByNameWithBrand(@Param("name") String name, @Param("brand") String brand);

    @Query("select p from Product p where upper(p.name) like %:name%")
    public List<Product> findByName(@Param("name") String name);

    @Query("select p from Product p where upper(p.brand) like %:brand% and p.category.id = :categoryId")
    public List<Product> findByBrandWithCategoryId(@Param("brand") String brand, @Param("categoryId") int categoryId);

    @Query("select p from Product p where p.category.id = :categoryId")
    public List<Product> findByCategory(@Param("categoryId") int categoryId);

    @Query("select p from Product p where upper(p.name) like %:name% and upper(p.brand) like %:brand% and p.category.id = :categoryId")
    public List<Product> findByNameWithBrandWithCategoryId(@Param("brand") String brand, @Param("name") String name, @Param("categoryId") int categoryId);
}
