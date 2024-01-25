package org.example.service;

import org.example.DTO.NewShippingInfo;
import org.example.entity.ShippingInfo;

import java.util.List;

public interface ShippingInfoService {
    ShippingInfo getShippingInfoByAllFields(ShippingInfo shippingInfo);
    ShippingInfo addShippingInfoToUser(Long userId, NewShippingInfo newShippingInfo);
    ShippingInfo addOrGetShippingInfo(NewShippingInfo newShippingInfo);

    List<ShippingInfo> getAllShippingInfosByUserId(Long userId);

    ShippingInfo getShippingInfoById(Long shippingInfoId);

    List<ShippingInfo> getAllShippingInfo();
}
