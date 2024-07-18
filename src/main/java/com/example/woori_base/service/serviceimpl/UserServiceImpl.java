package com.example.woori_base.service.serviceimpl;

import com.example.woori_base.cofig.TokenConfig;
import com.example.woori_base.cofig.UserDetailServiceConfig;
import com.example.woori_base.dto.req.LoginReq;
import com.example.woori_base.dto.req.UserReq;
import com.example.woori_base.dto.res.LoginRes;
import com.example.woori_base.dto.res.UserRes;
import com.example.woori_base.entity.PartnerEntity;
import com.example.woori_base.entity.TokenEntity;
import com.example.woori_base.enums.TokenType;
import com.example.woori_base.exception.BusinessException;
import com.example.woori_base.repository.TokenRepository;
import com.example.woori_base.repository.UserRepository;
import com.example.woori_base.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder mpasswordEncoder;

    private final AuthenticationManager authenticationManager;

    private final TokenConfig tokenConfig;

    private final UserDetailServiceConfig userDetailServiceConfig;

    private final TokenRepository tokenRepository;

    @Value("${JWT_TIME_ACCESS_TOKEN}")
    private Long JWT_TIME_ACCESS_TOKEN;

    @Override
    public UserRes createAccount(UserReq userReq) {
        PartnerEntity partner = userReq.toPartnerEntity();

        if (userRepository.existsByEmail(userReq.getEmail())) {
            throw new BusinessException();
        }
        String random = RandomStringUtils.random(8, "1234567890");

        partner.setApCusNo(random);
        partner.setEmail(userReq.getEmail());
        partner.setPassword(mpasswordEncoder.encode(partner.getPassword()));
        partner.setCusNm(userReq.getName());
        partner.setTelNo(userReq.getTelNo());

        userRepository.save(partner);
        UserRes userRes = new UserRes();
        userRes.setApCusNo(partner.getApCusNo());
        userRes.setEmail(partner.getEmail());
        userRes.setCusNm(partner.getCusNm());
        return userRes;
    }

    @Override
    public LoginRes login(LoginReq loginReq) {
        LoginRes loginRes=new LoginRes();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginReq.getEmail(),
                    loginReq.getPassword()
            )
            );
        } catch (Exception e) {
            throw new BusinessException("Mật khẩu không chính xác");
        }
        // extraClaims có thể có hoặc không
        Map<String, Object> extraClaims = new HashMap<>();

        UserDetails userDetails = userDetailServiceConfig.loadUserByUsername(loginReq.getEmail());
        String accessToken = tokenConfig.generateToken(extraClaims ,userDetails);
        String refreshToken=tokenConfig.generateRefreshToken(userDetails);

        TokenEntity tokenEntity=new TokenEntity();
        tokenEntity.setAccessToken(accessToken);
        tokenEntity.setRefreshToken(refreshToken);
        tokenEntity.setTokenType(TokenType.BEARER);
        tokenEntity.setRevoked(false);
        tokenEntity.setExpired(false);
        tokenRepository.save(tokenEntity);

        loginRes.setToken_type("Bearer");
        loginRes.setScope("e-wallet");
        loginRes.setAccessToken(accessToken);
        loginRes.setExpiresIn(Math.toIntExact(JWT_TIME_ACCESS_TOKEN));
        return loginRes;
    }
}
