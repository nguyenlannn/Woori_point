package com.example.woori_base.cofig;

import com.example.woori_base.auditing.ApplicationAuditAware;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserDetailServiceConfig userDetailServiceConfig;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean// bean dùng cho xác định người dùng hiện tại,sau đó Spring Data JPA hỗ trợ tự động điền thông tin về người tạo và người cập nhật các thực thể bằng cách sử dụng @CreatedBy, @LastModifiedBy, @CreatedDate, và @LastModifiedDate.
    public ApplicationAuditAware auditorAware() {
        return new ApplicationAuditAware();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean// bean phải có cho mã hóa mật khẩu
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailServiceConfig);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
