//package com.example.woori_base.cofig;
//
//import com.example.woori_base.entity.PartnerEntity;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.Collections;
//
//@Data
//@AllArgsConstructor
//public class CustomUserDetailConfig implements UserDetails {
//
//    PartnerEntity partner;
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        //coi tất cả người dùng là ROLE_USER
//        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
//    }
//
//    @Override
//    public String getPassword() {
//        return partner.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return partner.getCusNm();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//}