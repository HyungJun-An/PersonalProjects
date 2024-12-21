package com.project.community.repository;

import com.project.community.domain.Board;
import com.project.community.domain.Like;
import com.project.community.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByBoardAndUser(Board board, User user);

    @Query("SELECT COUNT(l) FROM Like l WHERE l.board.board_id = :boardId")
    int countByBoardId(@Param("boardId") Long boardId);

    @Transactional
    @Modifying
    @Query("delete from Like l where l.board.board_id = :boardId")
    void deleteByBoardId(@Param("boardId") Long boardId);
}