package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "shipping_info")
public class ShippingInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String region;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String house;
    @Column(nullable = false)
    private String recipientFirstName;
    @Column(nullable = false)
    private String recipientLastName;
    @Column(nullable = false)
    private String recipientPhoneNumber;
    @ManyToMany(mappedBy = "shippingInfos")
    private Set<User> user;
}
