package com.project.community.board;

import com.project.community.domain.Board;
import com.project.community.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardServicesTest {

    @Autowired
    private BoardService boardService;

    @Test
    public void writeTest() {

        IntStream.rangeClosed(1,20).forEach(i -> {

            boardService.write("제목"+i, i+"번째내용","user"+i);
        });
    }

    @Test
    public void updateTest() {

        boardService.update(1L, "제목수정", "내용수정");
    }

    @Test
    public void searchTest() {
        List<Board> result =boardService.search("테스트", "", "");
        System.out.println(result + "!!!!!!!!!!!");
    }
}
