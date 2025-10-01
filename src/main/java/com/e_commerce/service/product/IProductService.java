package com.e_commerce.service.product;

import com.e_commerce.dto.ProductDto;
import com.e_commerce.model.Product;
import com.e_commerce.request.AddProductRequest;
import com.e_commerce.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {

    /**
     * Adds a new product to the system based on the provided request.
     * @param request The request object containing details for the new product.
     * @return The product object that was added.
     */
    Product addProduct(AddProductRequest request);

    /**
     * Retrieves a product by its unique identifier.
     * @param id The ID of the product to retrieve.
     * @return The product object corresponding to the given ID.
     * @throws com.e_commerce.exceptions.ProductNotFoundException if product with given ID is not found.
     */
    Product getProductById(Long id);

    /**
     * Deletes a product from the system by its unique identifier.
     * @param id The ID of the product to delete.
     * @throws com.e_commerce.exceptions.ProductNotFoundException if product with given ID is not found.
     */
    void deleteProductById(Long id);

    /**
     * Updates an existing product in the system.
     * @param request The request object containing updated product information.
     * @param productId The ID of the product to update.
     * @return The updated product object.
     * @throws com.e_commerce.exceptions.ProductNotFoundException if product with given ID is not found.
     */
    Product updateProduct(ProductUpdateRequest request, Long productId);

    /**
     * Retrieves a list of all products in the system.
     * @return A list of all product objects.
     */
    List<Product> getAllProducts();

    /**
     * Retrieves a list of products filtered by category name.
     * @param categoryName The name of the category to filter by.
     * @return A list of product objects belonging to the specified category.
     */
    List<Product> getProductByCategory(String categoryName);

    /**
     * Retrieves a list of products filtered by brand name.
     * @param brandName The name of the brand to filter by.
     * @return A list of product objects belonging to the specified brand.
     */
    List<Product> getProductByBrand(String brandName);

    /**
     * Retrieves a list of products filtered by both category and brand name.
     * @param categoryName The name of the category to filter by.
     * @param brandName The name of the brand to filter by.
     * @return A list of product objects belonging to the specified category and brand.
     */
    List<Product> getProductByCategoryAndBrand(String categoryName, String brandName);

    /**
     * Retrieves a list of products filtered by product name.
     * @param productName The name of the product to filter by.
     * @return A list of product objects matching the specified name.
     */
    List<Product> getProductByName(String productName);

    /**
     * Retrieves a list of products filtered by both brand and product name.
     * @param brandName The name of the brand to filter by.
     * @param productName The name of the product to filter by.
     * @return A list of product objects belonging to the specified brand and matching the product name.
     */
    List<Product> getProductByBrandAndName(String brandName, String productName);

    /**
     * Counts the number of products filtered by both brand and product name.
     * @param brandName The name of the brand to filter by.
     * @param productName The name of the product to filter by.
     * @return The count of product objects belonging to the specified brand and matching the product name.
     */
    Long countProductsByBrandAndName(String brandName, String productName);

    /**
     * Converts a Product entity to a ProductDto.
     * @param product The Product entity to convert.
     * @return The corresponding ProductDto.
     */
    ProductDto convertToDto(Product product);
}