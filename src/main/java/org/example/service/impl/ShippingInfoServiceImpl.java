package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.DTO.NewShippingInfo;
import org.example.entity.ShippingInfo;
import org.example.entity.User;
import org.example.exception.UserNotFoundException;
import org.example.repository.ShippingInfoRepository;
import org.example.service.ShippingInfoService;
import org.example.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingInfoServiceImpl implements ShippingInfoService {
    private final ShippingInfoRepository shippingInfoRepository;
    private final UserService userService;
    @Override
    public ShippingInfo getShippingInfoByAllFields(ShippingInfo shippingInfo) {
        return shippingInfoRepository.findByAllFields(
                shippingInfo.getRegion(),
                shippingInfo.getCity(),
                shippingInfo.getStreet(),
                shippingInfo.getHouse(),
                shippingInfo.getRecipientFirstName(),
                shippingInfo.getRecipientLastName(),
                shippingInfo.getRecipientPhoneNumber()
        );
    }

    @Transactional
    @Override
    public ShippingInfo addShippingInfoToUser(Long userId, NewShippingInfo newShippingInfo) {
        User user = userService.getUserById(userId);

        if (user == null){
            throw new UserNotFoundException("User with id " +
                    userId + " not found!");
        }

        ShippingInfo userShippingInfo = addOrGetShippingInfo(newShippingInfo);

        user.getShippingInfos().add(userShippingInfo);

        userService.saveUser(user);

        return userShippingInfo;
    }

    @Transactional
    @Override
    public ShippingInfo addOrGetShippingInfo(NewShippingInfo newShippingInfo) {
        ShippingInfo transientShippingInfo = ShippingInfo.builder()
                .region(newShippingInfo.getRegion())
                .city(newShippingInfo.getCity())
                .street(newShippingInfo.getStreet())
                .house(newShippingInfo.getHouse())
                .recipientFirstName(newShippingInfo.getRecipientFirstName())
                .recipientLastName(newShippingInfo.getRecipientLastName())
                .recipientPhoneNumber(newShippingInfo.getRecipientPhoneNumber())
                .build();

        ShippingInfo persistantShippingInfo = getShippingInfoByAllFields(transientShippingInfo);

        if (persistantShippingInfo == null){
            return shippingInfoRepository.save(transientShippingInfo);
        }

        return persistantShippingInfo;
    }

    @Override
    public List<ShippingInfo> getAllShippingInfosByUserId(Long userId) {
        return shippingInfoRepository.findAllByUserId(userId);
    }

    @Override
    public ShippingInfo getShippingInfoById(Long shippingInfoId) {
        return shippingInfoRepository.findById(shippingInfoId).orElse(null);
    }

    @Override
    public List<ShippingInfo> getAllShippingInfo() {
        return shippingInfoRepository.findAll();
    }
}
