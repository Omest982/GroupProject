package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Image;
import org.example.repository.ImageRepository;
import org.example.service.ImageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Override
    public Image addImage(String imageLink) {
         Image transientImage = Image.builder()
                .imageLink(imageLink)
                .build();
         return imageRepository.save(transientImage);
    }

    @Override
    public List<Image> addImages(List<String> imageLinks) {
        List<Image> transientImages = new ArrayList<>();

        for (String imageLink: imageLinks){
            Image transientImage = Image.builder()
                    .imageLink(imageLink)
                    .build();
            transientImages.add(transientImage);
        }
        return imageRepository.saveAll(transientImages);
    }
}
