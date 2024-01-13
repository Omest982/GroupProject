package org.example.service;

import org.example.entity.Image;

import java.util.List;
import java.util.Set;

public interface ImageService {
    Image addImage(String imageLink);

    Image addOrGetImage(String imageLink);

    List<Image> addOrGetImages(Set<String> imageLinks);

    Image getImageByImageLink(String imageLink);

}
