package com.project.community.controller;

import com.project.community.domain.User;
import com.project.community.repository.UserRepository;
import com.project.community.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("login")
    public String login() {
        return "/user/login";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/board/list";
    }

    @PostMapping("loginproc")
    public String loginproc(@RequestParam("id") String id,
                            @RequestParam("password") String password,
                            HttpServletRequest request,
                            RedirectAttributes redirectAttributes) {

        boolean success = userService.loginAvailable(id, password);
        if (success) {
            //로그인 성공시 게시글 조회 리다이렉트
            User user = userRepository.findById(id).orElseThrow(RuntimeException::new);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return "redirect:/board/list";
        } else {
            // 실패 시 에러 메세지 로그인화면 리다이렉트
            redirectAttributes.addFlashAttribute("errorMessage", "아이디 또는 비밀번호가 일치하지 않습니다");
            return "redirect:/user/login";
        }
    }

    @GetMapping("signup")
    public String signup() {
        return "/user/signup";
    }

    @PostMapping("signupproc")
    public String signupProc(String id, String nickname, String email, String password) {

        userService.signup(id, email, nickname, password);

        return "redirect:/user/login";
    }
}