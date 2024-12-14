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

    public void write(String title, String content) {
        Board board = Board.builder()
                .title(title)
                .content(content)
                .build();
        boardRepository.save(board);
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    public void update(String title, String content) {

    }
}
