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
    private List<String> categoryNames;
    private String brandName;
    private Sex sex;
    private Classification classification;
    private boolean isLiquid;
    private Map<String, Object> additionalInfo;
    private List<String> countriesMadeInNames;
    private String countryTradeMarkName;
}
