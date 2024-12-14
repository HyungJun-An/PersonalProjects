package com.project.community.repository;

import com.project.community.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE Board b SET b.title = :title, b.content= :content WHERE b.board_id =:board_id")
    void updateBoard(@Param("board_id") Long board_id,
                     @Param("title") String title,
                     @Param("content") String content);

    @Query(value = "SELECT title, content, writer FROM board WHERE title LIKE :title", nativeQuery = true)
    List<Board> findBoardByTitle(@Param("title") String title);

}
