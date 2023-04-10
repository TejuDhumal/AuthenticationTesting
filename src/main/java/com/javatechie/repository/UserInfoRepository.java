package com.javatechie.repository;

import com.javatechie.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {
    UserInfo findByName(String username);


    UserInfo findUserByEmail(String email);
}
