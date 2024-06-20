package com.example.woori_base.repository;

import com.example.woori_base.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {

}
