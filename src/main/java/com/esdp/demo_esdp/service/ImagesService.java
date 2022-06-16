package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.dto.ImagesDTO;
import com.esdp.demo_esdp.entity.Images;
import com.esdp.demo_esdp.entity.Product;
import com.esdp.demo_esdp.exception.ResourceNotFoundException;
import com.esdp.demo_esdp.repositories.ImagesRepository;
import com.esdp.demo_esdp.util.FileStorageImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImagesService {
    private final FileStorageImpl fileStorage;
    private final ImagesRepository imagesRepository;
    private final ProductService productService;

    @Value("${upload.path}")
    private String uploadPath;

    public void saveImagesFile(List<MultipartFile> files, Product product) {
        try {
            if (files != null) {
                File upload = new File(uploadPath);
                if (!upload.exists()) {
                    upload.mkdir();
                }
                for (int i = 0; i < files.size(); i++) {
                    String uuid = UUID.randomUUID().toString();
                    String resulFileName = uuid + "." + files.get(i).getOriginalFilename();
                    files.get(i).transferTo(new File(uploadPath + resulFileName));
                    imagesRepository.save(Images.builder()
                            .path(resulFileName)
                            .product(product)
                            .build());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteImagesFile(Long productId) {
        var imageProduct = imagesRepository.getImagesProduct(productId);
        if (!imageProduct.isEmpty()) {
            imageProduct.forEach(i -> imagesRepository.deleteById(i.getId()));
        }
        var paths = imagesRepository.getProductImagePath(productId);
        paths.forEach(i -> {
            try {
                Files.delete(Paths.get(uploadPath + i));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @SneakyThrows
    protected void replaceImageFile(MultipartFile newImage, String oldImageName) {
        fileStorage.rewrite(newImage.getInputStream(), oldImageName);
    }

    public List<ImagesDTO> getImagesDTObyProductId(Long productId) {
        return imagesRepository.getImagesProduct(productId).stream().map(ImagesDTO::from).collect(Collectors.toList());
    }

    public List<String> getImagesPathsByProductId(Long productId) {
        return imagesRepository.getProductImagePath(productId);
    }

    public ImagesDTO getImageDTObyId(Long imageId) {
        return ImagesDTO.from(getImageById(imageId));
    }

    protected Images getImageById(Long imageId) {
        Optional<Images> imageOpt = imagesRepository.findById(imageId);
        if (imageOpt.isPresent()) {
            return imageOpt.get();
        } else {
            throw new ResourceNotFoundException(String.format("Image with id %s was not found", imageId));
        }
    }


    public void deleteImageId(Long imageId) {
        var image = imagesRepository.getImageId(imageId)
                .orElseThrow(ResourceNotFoundException::new);
        imagesRepository.delete(image);
        try {
            Files.delete(Paths.get(uploadPath + image.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
