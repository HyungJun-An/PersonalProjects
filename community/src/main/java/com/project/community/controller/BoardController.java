package com.project.community.controller;


import com.project.community.domain.Board;
import com.project.community.repository.BoardRepository;
import com.project.community.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.util.List;


@Controller
@Log4j2
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;

    @GetMapping("/modify/{id}") //게시글 수정 GET 요청
    public String modifydo(@PathVariable Long id, Model model) {

        Board board = boardRepository.findById(id).orElseThrow(RuntimeException::new);
        model.addAttribute("board", board);

        return "/board/modify";
    }

    @PostMapping("/modifyproc/{id}")
    public String modifyproc(@PathVariable Long id, String title, String content) {

//        boardService.update(id, title, content);
        log.info("시작");
        Board result = boardRepository.findById(id).orElseThrow(RuntimeException::new);
        result.setTitle(title);
        result.setContent(content);
        boardRepository.save(result);
        log.info("끝");

        return "redirect:/board/list";
    }

    @GetMapping("/read/{id}") //상세 게시판
    public String read(@PathVariable("id")Long id, Model model) {

        Board result = boardRepository.findById(id).orElseThrow(RuntimeException::new);
        model.addAttribute("boards", result);

        return "board/read";
    }

    @GetMapping("/list") // 게시판 목록 GET요청
    public String list(@RequestParam(value = "type", required = false) String type,
                       @RequestParam(value = "input", required = false) String input,
                       @RequestParam(defaultValue = "0") int page,
                       Model model) {

        // 음수 값 방지
        int safePage = Math.max(0, page);

        // 한 페이지에 10개씩 보여주기
        Pageable pageable = PageRequest.of(safePage, 10, Sort.Direction.DESC,"created");
        Page<Board> boards;
        if( type != null && input != null && !type.isEmpty() && !input.isEmpty()) {
            boards = boardService.search(pageable, type, input);
        }
        else {
            boards = boardService.getBoards(pageable);
        }
        // 현재 페이지 그룹 계산
        int currentGroup = safePage / 10; // 현재 페이지 그룹 (0부터 시작)
        int startPage = currentGroup * 10 + 1; // 그룹의 시작 페이지 번호
        int endPage = Math.min((currentGroup + 1) * 10, boards.getTotalPages()); // 그룹의 끝 페이지 번호

        model.addAttribute("boards", boards);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "board/list";
    }

    @PostMapping("/list/search") //게시글 검색 POST 방식
    public String earchPost(@RequestParam("type")  String type,
                            @RequestParam("input") String input,
                            @RequestParam("page") int page) {

        String encodedType = URLEncoder.encode(type);
        String encodedInput = URLEncoder.encode(input);

        return "redirect:/board/list?page="+ page + "&type=" + encodedType + "&input=" + encodedInput;
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
