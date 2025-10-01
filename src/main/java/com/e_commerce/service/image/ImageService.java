package com.e_commerce.service.image;

import com.e_commerce.dto.ImageDto;
import com.e_commerce.exceptions.ImageNotFoundException;
import com.e_commerce.model.Image;
import com.e_commerce.model.Product;
import com.e_commerce.repository.ImageRepository;
import com.e_commerce.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException("Image with id " + " not found!"));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
            throw new ImageNotFoundException("Image with id " + " not found!");
        });
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImageDto = new ArrayList<>();
        for(MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileType(file.getContentType());
                image.setFileName(file.getOriginalFilename());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String downloadUrl = "/api/v1/images/image/download/" + image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage = imageRepository.save(image);

                ImageDto imageDto = new ImageDto();
                imageDto.setFileName(savedImage.getFileName());
                imageDto.setId(savedImage.getId());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImageDto.add(imageDto);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
