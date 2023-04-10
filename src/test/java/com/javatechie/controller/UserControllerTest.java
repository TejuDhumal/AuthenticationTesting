package com.javatechie.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.dto.AuthRequest;
import com.javatechie.entity.Product;
import com.javatechie.entity.UserInfo;
import com.javatechie.service.JwtService;
import com.javatechie.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserControllerTest {

//    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;


    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void addNewUser() {
        UserInfo userInfo = new UserInfo(1,"Demo1","demo1@gmail.com","12345678","ROLE_demo1");
        userController.addNewUser(userInfo);
//        System.out.println(userInfo);
        ArgumentCaptor<UserInfo> productArgumentCaptor = ArgumentCaptor.forClass(UserInfo.class);
        verify(userService).addUser(productArgumentCaptor.capture());
        UserInfo actualUserInfo = productArgumentCaptor.getValue();
        assertThat(actualUserInfo).isEqualTo(userInfo);


    }

    @Test
    void getAllUser() {
        List<UserInfo> expectedUsers = Arrays.asList(
                new UserInfo(1,"Demo1","demo1@gmail.com","12345678","ROLE_demo1"),
                new UserInfo(1,"Demo2","demo2@gmail.com","12345678","ROLE_demo2"),
                new UserInfo(1,"Demo3","demo3@gmail.com","12345678","ROLE_demo3")
        );
        when(userService.findAllUser()).thenReturn(expectedUsers);
        // act
        List<UserInfo> actualUsers = userController.getAllUser();

        assertThat(expectedUsers).isEqualTo(actualUsers);
    }



    @Test
    void testAuthRequest() throws Exception {
        AuthRequest request = new AuthRequest();
        request.setUsername("test_username");
        request.setPassword("test_password");

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(jwtService.generateToken(request.getUsername()))
                .thenReturn("test_token");
        when(authentication.isAuthenticated()).thenReturn(true);

        mockMvc.perform(post("/user/authentication")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("test_token"));
    }

}