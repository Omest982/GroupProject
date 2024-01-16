package org.example.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Classification;
import org.example.entity.enums.ProductStatus;
import org.example.entity.enums.Sex;
import org.hibernate.annotations.Type;

import java.util.*;

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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String productGroup;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //TODO: Разобраться почему не работает Set вместо List
    private List<ProductVariation> productVariations = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "products_images",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id", nullable = false),
            uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "image_id"})
    )
    private Set<Image> images = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false),
            uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "category_id"})
    )
    private Set<Category> categories = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus productStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Classification classification;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> additionalInfo = new HashMap<>();

    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "products_countries_made_in",
            joinColumns = @JoinColumn(name = "product_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "country_id", nullable = false)
    )
    private Set<Country> countriesMadeIn = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "country_trade_mark_id", nullable = false)
    private Country countryTradeMark;
}
