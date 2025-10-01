package com.e_commerce.repository;

import com.e_commerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryName(String categoryName);

    List<Product> findByBrand(String brandName);

    List<Product> findByCategoryNameAndBrand(String categoryName, String brandName);

    List<Product> findByName(String productName);

    List<Product> findByBrandAndName(String brandName, String productName);

    Long countByBrandAndName(String brandName, String productName);
}
