package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.util.FileStorageImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImagesService {
    private final FileStorageImpl fileStorage;

    //this method saves MultipartFile to "uploads_" in the root directory and returns a string of saved image name
    public String saveImage(MultipartFile image) throws IOException {
        String imageStorageName=fileStorage.save(image.getInputStream(),image.getOriginalFilename());
        return imageStorageName;
    }

    //this method deletes an image by its name saved in "uploads_" folder
    public void deleteImage(String imageName) throws IOException {
        try {
            fileStorage.delete(imageName);
        }catch (IOException e){
            throw new FileNotFoundException(String.format("cannot find image: %s",imageName));
        }
    }

    @SneakyThrows
    public void replaceImage(MultipartFile newImage, String oldImageName) {
        fileStorage.rewrite(newImage.getInputStream(),oldImageName);
    }
}
