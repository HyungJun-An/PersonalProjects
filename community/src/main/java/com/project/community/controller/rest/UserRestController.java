package com.project.community.controller.rest;

import com.project.community.domain.User;
import com.project.community.repository.UserRepository;
import com.project.community.service.LikeService;
import com.project.community.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@NoArgsConstructor
@Log4j2
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private UserRepository userRepository;

    /*
    아이디 중복확인
     */
    @GetMapping("check-id")
    public ResponseEntity<Boolean> checkId(@RequestParam("id") String id) {
        boolean available = userService.checkIdAvailable(id);
        return ResponseEntity.ok(available);
    }

    /*
    닉네임 중복확인
     */
    @GetMapping("check-nickname")
    public ResponseEntity<Boolean> checkNickname(@RequestParam("nickname") String nickname) {
        boolean available = userService.checkNicknameAvailable(nickname);
        return ResponseEntity.ok(available);
    }

    /*
    회원 삭제 시 비밀번호 확인
     */
    @GetMapping("verify-password")
    public ResponseEntity<Boolean> verifyPassword(@RequestParam("id")String id,@RequestParam("password") String password) {
        boolean confirm = userService.loginAvailable(id, password);

        if(confirm) {
            likeService.deleteUserLikes(id);
            userRepository.deleteById(id);
        }
        return ResponseEntity.ok(confirm);
    }

    /*
    게시글 좋아요 기능
     */
    @PostMapping("like/{boardId}")
    public ResponseEntity<Boolean> togglike(@PathVariable Long boardId, HttpSession session) {
        //현재 사용자 정보 가져오기
        User user = (User) session.getAttribute("user");

        if(user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
        boolean liked = likeService.toggleLike(boardId, user);
        return ResponseEntity.ok(liked);
    }

    /*
    게시글 좋아요 확인
     */
    @GetMapping("like-check/{boardId}")
    public ResponseEntity<Boolean> likeCheck(@PathVariable("boardId") Long boardId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
        boolean liked = likeService.liked(boardId, user);
        return ResponseEntity.ok(liked);
    }

    /*
    게시글 좋아요 개수 조회
     */
    @GetMapping("like-count/{boardId}")
    public ResponseEntity<Integer> getLikeCount(@PathVariable Long boardId) {
        int likeCount = likeService.getLikeCount(boardId);
        return ResponseEntity.ok(likeCount);
    }
}
