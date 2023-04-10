package com.javatechie.service;

import com.javatechie.entity.Product;
import com.javatechie.entity.UserInfo;
import com.javatechie.exception.RestExceptionHandler;
import com.javatechie.repository.UserInfoRepository;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserInfoRepository userRepository;

   @InjectMocks
    private UserService userService;

   @Mock
   private PasswordEncoder passwordEncoder;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.userService = new UserService(this.userRepository);

    }

//    @Test
//    void addUser() {
//        UserInfo userInfo = new UserInfo();
//        userInfo.setId(1);
//        userInfo.setName("Demo1");
//        userInfo.setEmail("demo1@gmail.com");
//        userInfo.setPassword("12345678");
//        userInfo.setRoles("ROLE_demo1");
//       when(userService.addUser(userInfo)).thenReturn("user added successfully");
//        when(passwordEncoder.encode(anyString())).thenReturn("cGFzc3dvcmQ=");
//        //        System.out.println(userInfo);
////        ArgumentCaptor<UserInfo> productArgumentCaptor = ArgumentCaptor.forClass(UserInfo.class);
////        verify(userRepository).save(productArgumentCaptor.capture());
//        String actualUserInfo = userService.addUser(userInfo);
//        assertThat(actualUserInfo).isEqualTo(userInfo);
//    }

    @Test
    void findAllUser() {
        List<UserInfo> expectedUsers = Arrays.asList(
                new UserInfo(1,"Demo1","demo1@gmail.com","12345678","ROLE_demo1"),
                new UserInfo(1,"Demo2","demo2@gmail.com","12345678","ROLE_demo2"),
                new UserInfo(1,"Demo3","demo3@gmail.com","12345678","ROLE_demo3")
        );
        when(userRepository.findAll()).thenReturn(expectedUsers);
        // act
        List<UserInfo> actualUsers = userService.findAllUser();

        assertThat(expectedUsers).isEqualTo(actualUsers);
    }

    @Test
    void getUserByEmail() {
        UserInfo expectedUser = new UserInfo();
        expectedUser.setId(1);
        expectedUser.setName("Demo");
        expectedUser.setEmail("demo@gmail.com");
        expectedUser.setPassword("12345679");
        expectedUser.setRoles("ROLE_DEMO");

        when(userRepository.findUserByEmail(anyString())).thenReturn(expectedUser);

        UserInfo responseEntity = userService.getUserByEmail("demo@gmal.com");

        assertAll(
                "propertes",
                () ->assertEquals("Demo",responseEntity.getName()),
                () ->assertEquals("demo@gmail.com",responseEntity.getEmail()),
                () ->assertEquals("12345679",responseEntity.getPassword()),
                () ->assertEquals("ROLE_DEMO",responseEntity.getRoles())
        );


    }

//    @Test(expected = RuntimeException.class)
//    public void testGetUserByEmailNotFound() {
//        // Call the getUserByEmail function with an email that doesn't exist
//        UserInfo result = userService.getUserByEmail("nonexistent@example.com");
//    }
}