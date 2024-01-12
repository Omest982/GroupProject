package org.example.service;

import org.example.entity.Image;

import java.util.List;

public interface ImageService {
    Image addImage(String imageLink);

    Image addOrGetImage(String imageLink);

    List<Image> addOrGetImages(List<String> imageLinks);

    Image getImageByImageLink(String imageLink);

}
