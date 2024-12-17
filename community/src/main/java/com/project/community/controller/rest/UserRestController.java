package com.project.community.controller.rest;

import com.project.community.service.UserService;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@NoArgsConstructor
@Log4j2
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("check-id")
    public ResponseEntity<Boolean> checkId(@RequestParam("id") String id) {
        boolean available = userService.checkIdAvailable(id);
        return ResponseEntity.ok(available);
    }

    @GetMapping("check-nickname")
    public ResponseEntity<Boolean> checkNickname(@RequestParam("nickname") String nickname) {
        boolean available = userService.checkNicknameAvailable(nickname);
        return ResponseEntity.ok(available);
    }

}
