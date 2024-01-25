package org.example.DTO;

import lombok.Data;
import org.example.entity.enums.PaymentMethod;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class NewOrder {
    private PaymentMethod paymentMethod;
    private String userComment;
    //Can add more fields
}
