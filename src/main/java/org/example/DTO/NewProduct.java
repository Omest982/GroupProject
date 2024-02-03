package org.example.DTO;

import lombok.*;
import org.example.entity.enums.Classification;
import org.example.entity.enums.ProductStatus;
import org.example.entity.enums.Sex;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewProduct {
    private String name;
    private Long categoryId;
    private Long brandId;
    private Set<String> imageLinks;
    private String productGroup;
    private Sex sex;
    private ProductStatus productStatus;
    private Classification classification;
    private Map<String, Object> additionalInfo;
    private String description;
    private Collection<Long> countriesMadeInIds;
    private Long countryTradeMarkId;
}
