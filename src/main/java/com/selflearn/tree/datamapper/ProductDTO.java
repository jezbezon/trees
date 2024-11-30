package com.selflearn.tree.datamapper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
public class ProductDTO {

    private String name;
    private String brand;
    private BigDecimal price;
    private int qty;
    private int categoryId;
    private String description;



}
