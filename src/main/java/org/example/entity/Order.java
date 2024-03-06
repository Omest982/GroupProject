package org.example.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.entity.enums.OrderStatus;
import org.example.entity.enums.PaymentMethod;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    @NotNull
    private Timestamp created;
    @UpdateTimestamp
    @NotNull
    private Timestamp updated;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    @NotNull
    private PaymentMethod paymentMethod;
    private String userComment;
    @Enumerated(EnumType.STRING)
    @NotNull
    private OrderStatus orderStatus;
    @NotNull
    private BigDecimal sum;
    @OneToOne
    @JoinColumn(name = "shipping_info_id", nullable = false)
    private ShippingInfo shippingInfo;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetails> orderDetails = new ArrayList<>();

}
