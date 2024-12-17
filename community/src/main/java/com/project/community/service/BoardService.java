package com.project.community.service;

import com.project.community.domain.Board;
import com.project.community.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    /*
    게시글 작성
     */
    public void write(String title, String content, String writer) {
        Board board = Board.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .build();
        boardRepository.save(board);
    }

    /*
    게시글 조회
     */
    public Page<Board> search(String title, String content, String writer, Pageable pageable) {
        Page<Board> result = boardRepository.findBoardByTitleContaining(title, pageable);

        return result;
    }

    /*
    게시글 페이징처리 및 정렬
     */
    public Page<Board> getBoards(Pageable pageable) {
        // Pageable에서 page와 size를 가져와 새 PageRequest에 정렬 조건 추가
        PageRequest sortedPage = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "created")
        );
        return boardRepository.findAll(sortedPage);
    }

    /*
    게시글 검색
     */
    public Page<Board> search(Pageable pageable,String type, String input) {

        Page<Board> result;
        switch(type){
            case "title":
                result = boardRepository.findBoardByTitleContaining(input, pageable);
                break;

            case "content":
                result = boardRepository.findBoardByContentContaining(input, pageable);
                break;

            case "writer":
                result = boardRepository.findBoardByWriterContaining(input, pageable);
                break;

            default:
                result = boardRepository.findBoardByTitleContaining(input, pageable);
        }
        return result;
    }

    /*
    게시글 수정
     */
    public void update(Long board_id, String title, String content) {

        boardRepository.updateBoard(board_id, title, content);
    }
}
