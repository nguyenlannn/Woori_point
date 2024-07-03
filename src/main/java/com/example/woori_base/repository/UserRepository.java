package com.example.woori_base.repository;

import com.example.woori_base.entity.PartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<PartnerEntity,String> {

}
