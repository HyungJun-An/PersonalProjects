package com.project.community.service;

import com.project.community.domain.Board;
import com.project.community.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void write(String title, String content, String writer) {
        Board board = Board.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .build();
        boardRepository.save(board);
    }

    public List<Board> search(String title, String content, String writer) {
        String temp = "%" + title + "%";
        List<Board> result = boardRepository.findBoardByTitle(temp);

        System.out.println("service result = "+result);
        return result;
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public void update(Long board_id, String title, String content) {

        boardRepository.updateBoard(board_id, title, content);
    }
}
