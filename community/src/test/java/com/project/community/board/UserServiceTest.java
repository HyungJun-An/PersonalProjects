package com.project.community.board;

import com.project.community.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void loginTest() {
        boolean temp = userService.loginAvailable("없는아이디", "1234");
        System.out.println("결과"+temp);
    }
}
