package com.e_commerce.service.image;

import com.e_commerce.dto.ImageDto;
import com.e_commerce.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    /**
     * Retrieves an image by its unique identifier.
     *
     * @param id The unique identifier of the image to retrieve.
     * @return The Image object if found.
     */
    Image getImageById(Long id);
    
    /**
     * Deletes an image by its unique identifier.
     *
     * @param id The unique identifier of the image to delete.
     */
    void deleteImageById(Long id);
    
    /**
     * Saves multiple image files associated with a product.
     *
     * @param files List of MultipartFile objects representing the images to save.
     * @param productId The unique identifier of the product to associate with these images.
     * @return List of ImageDto objects containing information about the saved images.
     */
    List<ImageDto> saveImage(List<MultipartFile> files, Long productId);
    
    /**
     * Updates an existing image with a new file.
     *
     * @param file The new MultipartFile to replace the existing image.
     * @param imageId The unique identifier of the image to update.
     */
    void updateImage(MultipartFile file, Long imageId);


}
