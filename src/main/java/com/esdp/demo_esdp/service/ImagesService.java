package com.esdp.demo_esdp.service;

import com.esdp.demo_esdp.dto.ImagesDTO;
import com.esdp.demo_esdp.entity.Images;
import com.esdp.demo_esdp.exception.ResourceNotFoundException;
import com.esdp.demo_esdp.repositories.ImagesRepository;
import com.esdp.demo_esdp.util.FileStorageImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImagesService {
    private final FileStorageImpl fileStorage;
    private final ImagesRepository imagesRepository;
    private final ProductService productService;

    //this method saves MultipartFile to "uploads_" in the root directory and returns a string of saved image name
    protected String saveImageFile(MultipartFile image) throws IOException {
        String imageStorageName=fileStorage.save(image.getInputStream(),image.getOriginalFilename());
        return imageStorageName;
    }

    //this method deletes an image by its name saved in "uploads_" folder
    protected void deleteImageFile(String imageName) throws IOException {
        try {
            fileStorage.delete(imageName);
        }catch (IOException e){
            throw new FileNotFoundException(String.format("cannot find image: %s",imageName));
        }
    }

    @SneakyThrows
    protected void replaceImageFile(MultipartFile newImage, String oldImageName) {
        fileStorage.rewrite(newImage.getInputStream(),oldImageName);
    }

    public List<ImagesDTO> getImagesDTObyProductId(Long productId){
        return imagesRepository.getImagesProduct(productId).stream().map(ImagesDTO::from).collect(Collectors.toList());
    }

    public List<String> getImagesPathsByProductId(Long productId){
        return imagesRepository.getProductImagePath(productId);
    }

    public ImagesDTO getImageDTObyId(Long imageId){
        return  ImagesDTO.from(getImageById(imageId));
    }

    protected Images getImageById(Long imageId){
        Optional<Images> imageOpt=imagesRepository.findById(imageId);
        if(imageOpt.isPresent()){
            return  imageOpt.get();
        }else{
            throw new ResourceNotFoundException(String.format("Image with id %s was not found",imageId));
        }
    }

    public void saveNewImages(List<MultipartFile> files,Long productId) throws IOException {
        List<Images> images=new ArrayList<>();
        for(int i=0;i<files.size();i++){
            images.add(Images.builder()
                            .product(productService.findProductById(productId))
                            .path(saveImageFile(files.get(i)))
                    .build());
        }
        imagesRepository.saveAll(images);
    }

    @SneakyThrows
    public void deleteImagesById(Long imageId) {
        Optional<Images> imageOpt=imagesRepository.findById(imageId);
        if(imageOpt.isPresent()){
            deleteImageFile(imageOpt.get().getPath());
            imagesRepository.deleteById(imageOpt.get().getId());
        }
    }

    public void deleteImagesByProductId(Long productId){
        List<Images> images=imagesRepository.getImagesProduct(productId);
        images.forEach(img-> deleteImagesById(img.getId()));

    }

}
