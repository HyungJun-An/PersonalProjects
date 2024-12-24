package com.project.community.service;

import com.project.community.domain.Board;
import com.project.community.domain.Like;
import com.project.community.domain.User;
import com.project.community.repository.BoardRepository;
import com.project.community.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final BoardRepository boardRepository;

    public int getLikeCount(Long boardId) {
        return likeRepository.countByBoardId(boardId);
    }

    /*
    게시글 좋아요 눌렀는지 확인
     */
    public boolean liked(Long boardId, User user) {

        // Board 엔티티 조회
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        // 이미 좋아요 눌렀는지 확인
        Optional<Like> existingLikeOpt = likeRepository.findByBoardAndUser(board, user);
        if (existingLikeOpt.isPresent()) {
            return true;
        }
        return false;
    }

    /*
    좋아요 눌렀을때 누른 상태인지 아닌지 확인
     */
    @Transactional
    public boolean toggleLike(Long boardId, User user) {

        Board board = boardRepository.findById(boardId) // 추후 liked 랑 겹쳐서 수정해야 함..
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Like existingLike = null;

        if(liked(boardId, user)) {
            existingLike = likeRepository.findByBoardAndUser(board, user).orElseThrow();
        }

        if (existingLike != null) {
            // 좋아요 취소
            likeRepository.delete(existingLike);
            board.setLikes(board.getLikes() -1);
            boardRepository.save(board);
            return false; //좋아요 취소 상태
        } else {
            // 좋아요 추가
            Like like = Like.builder().board(board).user(user).build();
            likeRepository.save(like);
            board.setLikes(board.getLikes() + 1);
            boardRepository.save(board);
            return true; // 좋아요 성공 상태
        }
    }
    /*
    글 삭제시 해당 글 좋아요 모두 삭제
     */
    public void deleteBoardLikes(Long boardId) {
        likeRepository.deleteByBoardId(boardId);
    }
}