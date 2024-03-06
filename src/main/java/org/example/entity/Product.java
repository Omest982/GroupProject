package org.example.entity;

import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.entity.enums.Classification;
import org.example.entity.enums.ProductStatus;
import org.example.entity.enums.Sex;
import org.hibernate.annotations.Type;
import org.springframework.lang.NonNull;

import java.util.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product implements Comparable<Product> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String name;

    @NotNull
    private String productGroup;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //TODO: Разобраться почему не работает Set вместо List
    private List<ProductVariation> productVariations = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "products_images",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id", nullable = false)
    )
    private Set<Image> images = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    )
    private Set<Category> categories = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "brand_id")
    @NotNull
    private Brand brand;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ProductStatus productStatus;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Sex sex;

    @Enumerated(EnumType.STRING)
    @NotNull
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
    @NotNull
    private Country countryTradeMark;

    @Override
    public int compareTo(@NonNull Product otherProduct) {
        return this.name.compareTo(otherProduct.getName());
    }
}
