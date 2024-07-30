package com.example.woori_base.repository;

import com.example.woori_base.entity.PartnerEntity;
import com.example.woori_base.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity,String> {

//    @Query(value = """
//      select t from Token t inner join partner p\s
//      on t.user.id = p.id\s
//      where u.id = :id and (t.expired = false or t.revoked = false)\s
//      """)
//    List<TokenEntity> findAllValidTokenByUser(Integer id);

    Optional<TokenEntity> findByAccessToken(String accessToken);
}
