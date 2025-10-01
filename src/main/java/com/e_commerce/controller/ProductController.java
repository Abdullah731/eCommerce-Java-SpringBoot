package com.e_commerce.controller;

import com.e_commerce.dto.ProductDto;
import com.e_commerce.exceptions.ProductNotFoundException;
import com.e_commerce.model.Product;
import com.e_commerce.request.AddProductRequest;
import com.e_commerce.request.ProductUpdateRequest;
import com.e_commerce.response.ApiResponse;
import com.e_commerce.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/get-all-products")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        if(productList.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Not found", NOT_FOUND));
        }
        return ResponseEntity.ok().body(new ApiResponse("Found", productList));
    }

    @GetMapping("/get-product/id/{productId}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
        try {
            Product product = productService.getProductById(productId);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Found", product));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Product Id " + productId + " not found", NOT_FOUND));
        }
    }

    @PostMapping("/add-product")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest productRequest) {
        try {
            Product product = productService.addProduct(productRequest);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Added", productDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping("/update-product/id/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest productRequest, @PathVariable Long productId) {
        try {
            Product product = productService.updateProduct(productRequest, productId);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Product updated", product));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Product not found!", NOT_FOUND));
        }
    }

    @DeleteMapping("/delete-product/id/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Product id " + productId + " deleted!", null));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Product id " + productId + " not found", NOT_FOUND));
        }
    }

    @DeleteMapping("/delete-all-products")
    public ResponseEntity<ApiResponse> deleteAllProduct() {
        List<Product> productList = productService.getAllProducts();
        for(Product product : productList) {
            try {
                productService.deleteProductById(product.getId());
            } catch (Exception e) {
                return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                        .body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
            }
        }
        return ResponseEntity.ok()
                .body(new ApiResponse("All products deleted", null));
    }

    @GetMapping("/get-product-by-brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName) {
        try {
            List<Product> productList = productService.getProductByBrandAndName(brandName, productName);
            if (productList.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("No product found", NOT_FOUND));
            }
            return ResponseEntity.ok()
                    .body(new ApiResponse("success", productList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/get-product-by-name/{productName}")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String productName) {
        try {
            List<Product> productList = productService.getProductByName(productName);
            if(productList.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("Product not found", NOT_FOUND));
            }
            return ResponseEntity.ok()
                    .body(new ApiResponse("Found", productList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/get-product-by-brand-name")
    public ResponseEntity<ApiResponse> getProductByBrandName(@RequestParam String brandName) {
        try {
            List<Product> productList = productService.getProductByBrand(brandName);
            if(productList.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("Product not found", NOT_FOUND));
            }
            return ResponseEntity.ok()
                    .body(new ApiResponse("Found", productList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/get-product-by-category/{categoryName}")
    public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String categoryName) {
        try {
            List<Product> productList = productService.getProductByCategory(categoryName);
            if(productList.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("Product not found", NOT_FOUND));
            }
            return ResponseEntity.ok()
                    .body(new ApiResponse("Found", productList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/get-product-by-category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String categoryName, @RequestParam String brandName) {
        try {
            List<Product> productList = productService.getProductByCategoryAndBrand(categoryName, brandName);
            if(productList.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse("Product not found", NOT_FOUND));
            }
            return ResponseEntity.ok()
                    .body(new ApiResponse("Found", productList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }
}
