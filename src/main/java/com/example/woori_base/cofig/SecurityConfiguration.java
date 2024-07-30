package com.example.woori_base.cofig;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {"/**",//tạo một list danh sách các url đc đi qua
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**"};
    private final FilterConfig filterConfig;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)// vô hiệu hóa bảo vệ csrf
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()// cho phép tất cả các url nằm trong danh sách (white list url) được đi qua
                                .anyRequest()// còn lại các url ko nằng trong ds, thì yêu cầu xác thực
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))//chính sách quản lí phiên làm việc
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(filterConfig, UsernamePasswordAuthenticationFilter.class)// cho đi qua bộ lọc mình custom.
        ;
        return http.build();
    }
}