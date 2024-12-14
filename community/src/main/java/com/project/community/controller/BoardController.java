package com.project.community.controller;


import com.project.community.domain.Board;
import com.project.community.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@Log4j2
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    @GetMapping("/list")
    public String list(Model model) {

        model.addAttribute("boards", boardService.findAll());
        return "board/list";
    }

    @GetMapping("/write")
    public String writedo() {
        log.info("write!!!!!!!!!!!!");
        return "board/write";
    }

    @PostMapping("/writeproc")
    public String writeproc(String title, String content) {
        log.info("title:"+title);
        log.info("content:"+content);


        boardService.write(title, content);

        return "redirect:/board/list";
    }
}
