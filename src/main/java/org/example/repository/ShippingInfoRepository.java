package org.example.repository;

import org.example.entity.ShippingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShippingInfoRepository extends JpaRepository<ShippingInfo, Long> {
    @Query(value = """
                SELECT s FROM ShippingInfo s
                WHERE upper(s.region) = upper(:region)
                AND upper(s.city) = upper(:city)
                AND upper(s.street) = upper(:street)
                AND upper(s.house) = upper(:house)
                AND upper(s.recipientFirstName) = upper(:recipientFirstName)
                AND upper(s.recipientLastName) = upper(:recipientLastName)
                AND upper(s.recipientPhoneNumber) = upper(:recipientPhoneNumber)
        """
    )
    Optional<ShippingInfo> findByAllFields(
            @Param("region") String region,
            @Param("city") String city,
            @Param("street") String street,
            @Param("house") String house,
            @Param("recipientFirstName") String recipientFirstName,
            @Param("recipientLastName") String recipientLastName,
            @Param("recipientPhoneNumber") String recipientPhoneNumber
            );

    @Query(value = """
            SELECT id, region, city, street, house,
            recipient_first_name, recipient_last_name, recipient_phone_number
            FROM shipping_info
            JOIN users_shipping_infos
            ON users_shipping_infos.shipping_info_id = shipping_info.id
            WHERE user_id = :userId
            """
            , nativeQuery = true
    )
    List<ShippingInfo> findAllByUserId(@Param("userId") Long userId);
}
