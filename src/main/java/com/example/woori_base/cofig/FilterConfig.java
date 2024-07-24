package com.example.woori_base.cofig;

import com.example.woori_base.repository.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FilterConfig extends OncePerRequestFilter {

    private final TokenConfig tokenConfig;

    private final UserDetailServiceConfig userDetailServiceConfig;

    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().contains("/basic")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = tokenConfig.extractUsername(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailServiceConfig.loadUserByUsername(userEmail);
            var isTokenValid = tokenRepository.findByAccessToken(jwt)
                    .map(t -> !t.isExpired() && !t.isRevoked())//để kiểm tra xem token có hết hạn hoặc bị thu hồi hay không
                    .orElse(false);
            if (tokenConfig.isTokenValid(jwt, userDetails) && isTokenValid) {//kiểm tra tính hợp lệ của token
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()// lấy danh sách quyền của user
                );
                authToken.setDetails( // Thiết lập thông tin chi tiết (details) cho authToken
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);//cho đối tượng xác thực vào context
            }
        }
        filterChain.doFilter(request, response);//tiếp tục lọc
    }
}
