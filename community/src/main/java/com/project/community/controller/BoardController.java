package com.project.community.controller;


import com.project.community.domain.Board;
import com.project.community.repository.BoardRepository;
import com.project.community.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@Log4j2
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;

    @GetMapping("/read/{id}") //상세 게시판
    public String read(@PathVariable("id")Long id, Model model) {

        Board result = boardRepository.findById(id).orElseThrow(RuntimeException::new);
        model.addAttribute("boards", result);

        return "board/read";
    }

    @GetMapping("/list") //게시판 목록
    public String list(Model model) {

        model.addAttribute("boards", boardService.findAll());
        return "board/list";
    }

    @GetMapping("/write") // 게시글 작성  GET 요청
    public String writedo() {
        log.info("write!!!!!!!!!!!!");
        return "board/write";
    }

    @PostMapping("/writeproc") // 게시글 작성 POST 요청
    public String writeproc(String title, String content, String writer) {
        log.info("title:"+title);
        log.info("content:"+content);


        boardService.write(title, content, writer);

        return "redirect:/board/list";
    }
}
