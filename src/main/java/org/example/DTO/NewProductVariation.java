package org.example.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewProductVariation {
    private String imageLink;
    private String variationName;
    private Long productId;
}
