package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.entity.utils.BusinessDetails;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id", callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "basket_details")
public class BasketDetails extends BusinessDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;
}
