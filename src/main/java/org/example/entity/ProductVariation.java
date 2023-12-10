package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_variation")
public class ProductVariation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "image_id")
    private Image variationImage;
    private double amount;
    @OneToMany(mappedBy = "productVariation")
    private List<VariationDetails> variationDetails;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
