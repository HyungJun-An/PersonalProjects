package com.project.community.repository;

import com.project.community.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findAll(Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Board b SET b.title = :title, b.content= :content WHERE b.board_id =:board_id")
    void updateBoard(@Param("board_id") Long board_id, //게시글 수정
                     @Param("title") String title,
                     @Param("content") String content);

    Page<Board> findBoardByTitleContaining(String title, Pageable pageable); //제목 검색

    Page<Board> findBoardByContentContaining(String content, Pageable pageable);

    Page<Board> findBoardByWriterContaining(String writer, Pageable pageable);

}
