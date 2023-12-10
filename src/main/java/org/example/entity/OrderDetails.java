package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.entity.utils.BusinessDetails;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id", callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_details")
public class OrderDetails extends BusinessDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
