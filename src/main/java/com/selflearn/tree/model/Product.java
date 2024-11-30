package com.selflearn.tree.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.selflearn.tree.datamapper.ProductDTO;
import com.selflearn.tree.resposeClass.AuditModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false, length = 128)
    private String name;

    @Column(name = "price" ,nullable = false)
    private BigDecimal price;

    @Column(name = "quantity", nullable = true)
    private int quantity = 0;

    @Column(name = "brand", unique = true)
    private String brand;

    @Column(name = "description", nullable = true)
    private String description;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;

    @OneToMany(mappedBy = "product" , cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Image> images;

    public String getCategoryName(){
        return category.getName();
    }

    public Product(String name, BigDecimal price, int quantity, String brand, String description, Category category) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.brand = brand;
        this.description = description;
        this.category = category;
    }

    public Product(Long id, String name, BigDecimal price, int quantity, String brand, String description, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.brand = brand;
        this.description = description;
        this.category = category;
    }

    public ProductDTO productDTO(Product product){
        return ProductDTO.builder()
                .name(product.getName())
                .qty(product.getQuantity())
                .price(product.getPrice())
                .description(product.getDescription())
                .brand(product.getBrand())
                .categoryId(product.category.getId())
                .build();

    }
}
