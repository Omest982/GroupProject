package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Image;
import org.example.repository.ImageRepository;
import org.example.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Transactional
    @Override
    public Image addImage(String imageLink) {
         Image transientImage = Image.builder()
                .imageLink(imageLink)
                .build();
         return imageRepository.save(transientImage);
    }

    @Transactional
    @Override
    public Image addOrGetImage(String imageLink) {
        Image image = getImageByImageLink(imageLink);
        if (image == null){
            return addImage(imageLink);
        }
        return image;
    }

    @Transactional
    @Override
    public List<Image> addOrGetImages(Set<String> imageLinks) {
        List<Image> imageList = new ArrayList<>();

        for (String imageLink: imageLinks){
            imageList.add(addOrGetImage(imageLink));
        }

        return imageList;
    }

    @Override
    public Image getImageByImageLink(String imageLink) {
        return imageRepository.findByImageLink(imageLink);
    }
}
