//package com.example.woori_base.cofig;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//@RequiredArgsConstructor
//public class JWTAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JWTConfig tokenProvider;
//
//    private final UserDetailServiceConfig userDetailServiceConfig;
//    @Override
//    protected void doFilterInternal(@NotNull HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//        try {
//            //lấy token từ request
//            String jwt = getJwtFromRequest(request);
//            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
//                // Lấy id user từ chuỗi jwt
//                String apCusNo = String.valueOf(tokenProvider.getUserIdFromJWT(jwt));
//                // Lấy thông tin người dùng từ id
//                UserDetails userDetails = userDetailServiceConfig.loadUserById(apCusNo);
//                if(userDetails != null) {
//                    // Nếu người dùng hợp lệ, set thông tin cho Seturity Context
//                    UsernamePasswordAuthenticationToken
//                            authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            }
//        }catch (Exception e){
//        }
//
//    }
//
//    //phương thức lấy jwt
//    private String getJwtFromRequest(HttpServletRequest request) {
//        //lấy header trong phần author
//        String bearerToken = request.getHeader("Authorization");
//        // Kiểm tra xem header Authorization có chứa thông tin jwt không
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//}
