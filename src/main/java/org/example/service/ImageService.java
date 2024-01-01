package org.example.service;

import org.example.entity.Image;

import java.util.List;

public interface ImageService {
    Image addImage(String imageLink);

    List<Image> addImages(List<String> imageLinks);
}
