package com.e_commerce.controller;

import com.e_commerce.exceptions.AlreadyExistsException;
import com.e_commerce.exceptions.CategoryNotFoundException;
import com.e_commerce.model.Category;
import com.e_commerce.response.ApiResponse;
import com.e_commerce.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping("/category/get-all-categories")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<Category> categoryList = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Found!", categoryList));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/category/add-category")
    public ResponseEntity<ApiResponse> addNewCategory(@RequestBody Category category) {
        try {
            Category newCategory = categoryService.addCategory(category);
            return ResponseEntity.ok(new ApiResponse("Added!", newCategory));
        } catch (AlreadyExistsException ex) {
            return ResponseEntity.status(CONFLICT)
                    .body(new ApiResponse(ex.getMessage(), null));

        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error!", INTERNAL_SERVER_ERROR + e.getMessage()));
        }
    }

    @PutMapping("/category/update-category/id/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long categoryId, @RequestBody Category category) {
        try {
            categoryService.updateCategory(category, categoryId);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Updated!", OK));
        } catch (CategoryNotFoundException ex) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Category " + category.getName() + " not found", NOT_FOUND));
        }
    }

    @GetMapping("/category/get-category/id/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId) {
        try {
            Category category = categoryService.getCategoryById(categoryId);
                return ResponseEntity.ok()
                        .body(new ApiResponse("Found!", category));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Category " + categoryId + " was not found", null));
        }
    }

    @GetMapping("/category/get-category/name/{categoryName}")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String categoryName) {
        Category category = categoryService.getCategoryByName(categoryName);
        if(category != null) {
            return ResponseEntity.ok()
                    .body(new ApiResponse("Found!", category));
        } else {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(categoryName + " category not found!", NOT_FOUND));
        }
    }

    @DeleteMapping("/category/delete-category/id/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable Long categoryId) {
        try {
            categoryService.deleteCategoryById(categoryId);
            return ResponseEntity.ok()
                    .body(new ApiResponse("Deleted!", OK));
        } catch (CategoryNotFoundException ex) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Category " + categoryId + " not found!", NOT_FOUND));
        }
    }

}
