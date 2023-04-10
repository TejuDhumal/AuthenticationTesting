package com.javatechie.service;

import com.javatechie.entity.UserInfo;
import com.javatechie.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
        return "user added successfully";

    }

    public List<UserInfo> findAllUser(){
        return userInfoRepository.findAll();
    }

    public UserInfo getUserByEmail(String email){
        UserInfo userInfo = userInfoRepository.findUserByEmail(email);
        if(userInfo == null){
            throw new RuntimeException("user by this email does not exist");
        }
        return userInfoRepository.findUserByEmail(email);
    }

//    public UserInfo deleteById(int id) {
//        return userInfoRepository.deleteById(id);
//    }

}
