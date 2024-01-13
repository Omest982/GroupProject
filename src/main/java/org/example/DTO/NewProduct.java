package org.example.DTO;

import lombok.Data;
import org.example.entity.enums.Classification;
import org.example.entity.enums.Sex;

import java.util.Map;
import java.util.Set;

@Data
public class NewProduct {
    private String name;
    private Iterable<Long> categoryIds;
    private Long brandId;
    private Set<String> imageLinks;
    private String productGroup;
    private Sex sex;
    private Classification classification;
    private Map<String, Object> additionalInfo;
    private String description;
    private Iterable<Long> countriesMadeInIds;
    private Long countryTradeMarkId;
}
