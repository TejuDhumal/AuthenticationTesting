package com.javatechie.repository;

import com.javatechie.User;
import com.javatechie.repo.UserRepository;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

//@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserInfoRepositoryTest {

 @Autowired
 private UserRepository userRepository;


    @BeforeEach
    void setUp() {


    }

    @Test
    public void testFindUserByEmail() {
        // given
        User user = new User();
        user.setId(2);
        user.setName("demo");
        user.setEmail("demo@gmail.com");
        user.setPassword("password");
        user.setRoles("ROLE_DEMO");
        userRepository.save(user);

        // when
        User actualUser = userRepository.findUserByEmail("demo@gmail.com");
        System.out.println(actualUser);

        // then
        assertThat(actualUser.getEmail()).isEqualTo(user.getEmail());
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();


    }
}