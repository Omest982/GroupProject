package org.example.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewOrderDetails {
    private Long variationDetailsId;
    private Integer quantity;
}
