package org.example.DTO;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
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
    private Sex sex;
    private Classification classification;
    private boolean isLiquid;
    private Map<String, Object> additionalInfo;
    private List<Long> countriesMadeInIds;
    private Long countryTradeMarkId;
}
