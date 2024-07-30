package com.example.woori_base.repository;

import com.example.woori_base.entity.PartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<PartnerEntity,String> {

    PartnerEntity findByEmail(String email);

    Boolean existsByEmail(String email);
}
