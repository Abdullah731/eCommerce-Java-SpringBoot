package com.e_commerce.service.product;

import com.e_commerce.dto.ImageDto;
import com.e_commerce.dto.ProductDto;
import com.e_commerce.exceptions.ProductNotFoundException;
import com.e_commerce.model.Category;
import com.e_commerce.model.Image;
import com.e_commerce.model.Product;
import com.e_commerce.repository.CategoryRepository;
import com.e_commerce.repository.ImageRepository;
import com.e_commerce.repository.ProductRepository;
import com.e_commerce.request.AddProductRequest;
import com.e_commerce.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Override
    public Product addProduct(AddProductRequest request) {
        // check if the category is found in the database.
        // If yes, set it as the new product category.
        // if no, then save it as a new category.
        // Then set it as a new product category.

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                 Category newCategory = new Category(request.getCategory().getName());
                 return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse( deletedProduct -> {
                        deletedProduct.setCategory(null);       // Set Category to be null first to detach the product element in DB
                        productRepository.delete(deletedProduct);
                        },
                        ()-> {throw new ProductNotFoundException("Product not found!");});
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository :: save)
                .orElseThrow(() -> new ProductNotFoundException("Product not found!"));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductByCategory(String categoryName) {
        return productRepository.findByCategoryName(categoryName);
    }

    @Override
    public List<Product> getProductByBrand(String brandName) {
        return productRepository.findByBrand(brandName);
    }

    @Override
    public List<Product> getProductByCategoryAndBrand(String categoryName, String brandName) {
        return productRepository.findByCategoryNameAndBrand(categoryName, brandName);
    }

    @Override
    public List<Product> getProductByName(String productName) {
        return productRepository.findByName(productName);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brandName, String productName) {
        return productRepository.findByBrandAndName(brandName, productName);
    }

    @Override
    public Long countProductsByBrandAndName(String brandName, String productName) {
        return productRepository.countByBrandAndName(brandName, productName);
    }

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        productDto.setImages(imageDtos);
        return productDto;
    }

    /**
     * Creates a new Product object from the provided AddProductRequest and Category.
     *
     * @param request The request object containing details for the new product.
     * @param category The category to which the product belongs.
     * @return A new Product object initialized with the provided details.
     */
    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getDescription(),
                request.getPrice(),
                request.getInventory(),
                category
        );
    }

    /**
     * Updates an existing Product object with details from the provided ProductUpdateRequest.
     *
     * @param existingProduct The existing Product object to be updated.
     * @param request The request object containing updated details for the product.
     * @return The updated Product object.
     */
    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setDescription(request.getDescription());
        existingProduct.setInventory(request.getInventory());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);

        return existingProduct;
    }
}
