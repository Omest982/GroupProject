package org.example.entity;

import jakarta.persistence.*;
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
    @Column(nullable = false)
    private Timestamp created;
    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp updated;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;
    private String userComment;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;
    @Column(nullable = false)
    private BigDecimal sum;
    @OneToOne
    @JoinColumn(name = "shipping_info_id", nullable = false)
    private ShippingInfo shippingInfo;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails = new ArrayList<>();

}
