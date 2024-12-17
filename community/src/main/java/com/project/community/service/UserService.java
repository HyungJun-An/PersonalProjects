package com.project.community.service;

import com.project.community.domain.User;
import com.project.community.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {

    private final UserRepository userRepository;

    /*
    회원가입
     */
    public void signup(String id, String nickname, String password, String email) {
        User user = User.builder()
                .id(id)
                .nickname(nickname)
                .password(password)
                .email(email)
                .build();

        userRepository.save(user);
    }

    /****************************
    ; Rest                      ;
    ****************************/

    /*
    아이디 중복체크
     */
    public boolean checkIdAvailable(String id) {
        return !userRepository.existsById(id);
    }

    /*
    닉네임 중복체크
     */
    public boolean checkNicknameAvailable(String nickname) {
        return !userRepository.existsByNickname(nickname);
    }

    /*
    ID PW 유효성 체크
     */
    public boolean loginAvailable(String id, String password) {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            return false;
        }

        User user = optionalUser.get();

        if (!user.getPassword().equals(password)) {
            return false;
        }
        return true;
    }
}
