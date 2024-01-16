package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.DTO.NewAddress;
import org.example.DTO.NewOrder;
import org.example.entity.Address;
import org.example.entity.User;
import org.example.entity.enums.UserRole;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Transactional
    @Override
    public User saveUser(User user){
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    @Transactional
    @Override
    public User checkIfUserExistsByNewOrderShortInfo(NewOrder order) {
        User user = getUserByPhoneNumber(order.getPhoneNumber());

        if (user == null){
            User transientUser = User.builder()
                    .phoneNumber(order.getPhoneNumber())
                    .firstName(order.getFirstName())
                    .lastName(order.getLastName())
                    .userRole(UserRole.CLIENT)
                    .isEmailVerified(false)
                    .enabled(true)
                    .build();

            user = saveUser(transientUser);
        }

        return user;
    }
}
