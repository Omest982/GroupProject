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
    public Image addOrGetImage(String imageLink) {
        Image image = getImageByImageLink(imageLink);
        if (image == null){
            return addImage(imageLink);
        }
        return image;
    }

    @Override
    public List<Image> addOrGetImages(List<String> imageLinks) {
        List<Image> imageList = new ArrayList<>();

        for (String imageLink: imageLinks){

            Image image = getImageByImageLink(imageLink);

            if (image == null){
                imageList.add(addImage(imageLink));
            }else {
                imageList.add(image);
            }

        }
        return imageList;
    }

    @Override
    public Image getImageByImageLink(String imageLink) {
        return imageRepository.findByImageLink(imageLink);
    }
}
