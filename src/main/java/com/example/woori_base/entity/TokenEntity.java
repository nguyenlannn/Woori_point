package com.example.woori_base.entity;

import com.example.woori_base.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Data
@Entity(name = "token")
@AllArgsConstructor
@NoArgsConstructor
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, columnDefinition = "text")
    private String accessToken;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    @Column(nullable = false, columnDefinition = "text")
    private String refreshToken;

    public boolean revoked;

    public boolean expired;

    private String apCusNo;//mã khách hàng
}
