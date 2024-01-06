package org.example.DTO;

import lombok.Data;
import org.example.entity.enums.Classification;
import org.example.entity.enums.Sex;

import java.util.List;
import java.util.Map;

@Data
public class NewProduct {
    private String name;
    private List<Long> categoryIds;
    private Long brandId;
    private List<String> imageLinks;
    private String productGroup;
    private Sex sex;
    private Classification classification;
    private Boolean isLiquid;
    private Map<String, Object> additionalInfo;
    private String description;
    private List<Long> countriesMadeInIds;
    private Long countryTradeMarkId;
}
