package com.project.community.user;

import com.project.community.repository.UserRepository;
import com.project.community.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void loginTest() {
        boolean temp = userService.loginAvailable("없는아이디", "1234");
        System.out.println("결과"+temp);
    }

    @Test
    public void delete() {
        userRepository.deleteById("아이디");

    }
}
