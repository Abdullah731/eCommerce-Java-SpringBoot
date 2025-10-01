package com.e_commerce.dto;

import com.e_commerce.model.Category;
import com.e_commerce.model.Image;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

//@AllArgsConstructor
@Data
public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int inventory;
    private Category category;
    private List<ImageDto> images;
}
