package org.example.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Classification;
import org.example.entity.enums.ProductStatus;
import org.example.entity.enums.Sex;
import org.hibernate.annotations.Type;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany
    @JoinColumn(name = "product_id")
    private List<ProductVariation> productVariations;

    @OneToMany
    @JoinColumn(name = "product_id")
    private List<Image> images;

    @ManyToMany
    @JoinTable(name = "products_categories",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @OneToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    private Classification classification;

    private boolean isLiquid;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private JsonBinaryType additionalInfo;

    @ManyToMany
    @JoinTable(name = "products_countries_made_in",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id"))
    private List<Country> countriesMadeIn;

    @OneToOne
    @JoinColumn(name = "country_trade_mark_id")
    private Country countryTradeMark;
}
