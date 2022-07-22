package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.dto.ImagesDTO;
import com.esdp.demo_esdp.entity.Images;
import com.esdp.demo_esdp.entity.Product;
import com.esdp.demo_esdp.exception.ProductNotFoundException;
import com.esdp.demo_esdp.exception.ResourceNotFoundException;
import com.esdp.demo_esdp.repositories.ImagesRepository;
import com.esdp.demo_esdp.repositories.ProductRepository;
import com.esdp.demo_esdp.util.FileStorageImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImagesService {
    private final FileStorageImpl fileStorage;
    private final ImagesRepository imagesRepository;
    private final ProductRepository productRepository;

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
                    Thumbnails.of(files.get(i).getInputStream())
                            .scale(0.7f)
                            .outputQuality(0.125f)
                            .toFile(new File(uploadPath + resulFileName));
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
        var paths = imagesRepository.getProductImagePath(productId);
        paths.forEach(i -> {
            try {
                Files.delete(Paths.get(uploadPath + i));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        var imageProduct = imagesRepository.getImagesProduct(productId);
        if (!imageProduct.isEmpty()) {
            imageProduct.forEach(i -> imagesRepository.deleteById(i.getId()));
        }
    }

    //this method saves MultipartFile to "uploads_" in the root directory and returns a string of saved image name
    protected String saveImageFile(MultipartFile image) throws IOException {
        String imageStorageName = fileStorage.save(image.getInputStream(), image.getOriginalFilename());
        return imageStorageName;
    }

    //this method deletes an image by its name saved in "uploads_" folder
    protected void deleteImageFile(String imageName) throws IOException {
        try {
            fileStorage.delete(imageName);
        } catch (IOException e) {
            throw new FileNotFoundException(String.format("cannot find image: %s", imageName));
        }
    }

    public void saveNewImages(List<MultipartFile> files, Long productId) throws IOException, ProductNotFoundException {
        List<Images> images = new ArrayList<>();
        for (int i = 0; i < files.size(); i++) {
            images.add(Images.builder()
                    .product(productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(String.format("product with id %s not found", productId))))
                    .path(saveImageFile(files.get(i)))
                    .build());
        }
        imagesRepository.saveAll(images);
    }

    @SneakyThrows
    public void deleteImagesById(Long imageId) {
        Optional<Images> imageOpt = imagesRepository.findById(imageId);
        if (imageOpt.isPresent()) {
            deleteImageFile(imageOpt.get().getPath());
            imagesRepository.deleteById(imageOpt.get().getId());
        }
    }

    public void deleteImagesByProductId(Long productId) {
        List<Images> images = imagesRepository.getImagesProduct(productId);
        images.forEach(img -> deleteImagesById(img.getId()));

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

        try {
            Files.delete(Paths.get(uploadPath + image.getPath()));
            imagesRepository.delete(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
