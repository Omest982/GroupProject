package org.example.DTO;

import lombok.Data;

@Data
public class NewProductVariation {
    private String imageLink;
    private String variationName;
    private Long productId;
}
