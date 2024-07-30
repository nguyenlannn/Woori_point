package com.example.woori_base.cofig;

import com.example.woori_base.entity.PartnerEntity;
import com.example.woori_base.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserDetailServiceConfig implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Kiểm tra xem user có tồn tại trong database không?
        PartnerEntity partner = userRepository.findByEmail(email);
        if (partner == null) {
            throw new UsernameNotFoundException(email + "user not found");
        }
        return new User(partner.getEmail(), partner.getPassword(), new ArrayList<>());
    }
}
