//package com.example.woori_base.cofig;
//
//import com.example.woori_base.entity.PartnerEntity;
//import com.example.woori_base.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class UserDetailServiceConfig implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String apCusNo) throws UsernameNotFoundException {
//        // Kiểm tra xem user có tồn tại trong database không?
//        PartnerEntity partner = userRepository.findByApCusNo(apCusNo);
//        if (partner == null) {
//            throw new UsernameNotFoundException(apCusNo);
//        }
//        return new CustomUserDetailConfig(partner);
//    }
//
//    public UserDetails loadUserById(String id) {
//        PartnerEntity partner = userRepository.findById(id).orElseThrow(
//                () -> new UsernameNotFoundException("User not found with id : " + id)
//        );
//        return new CustomUserDetailConfig(partner);
//    }
//
//}
