package com.sparta.plusweek.domain.service;

import com.sparta.plusweek.domain.dto.CommentCreateRequestDto;
import com.sparta.plusweek.domain.dto.CommentEditRequestDto;
import com.sparta.plusweek.domain.dto.CommentResponseDto;
import com.sparta.plusweek.domain.entity.Comment;
import com.sparta.plusweek.domain.entity.Post;
import com.sparta.plusweek.domain.entity.User;
import com.sparta.plusweek.domain.repository.CommentRepository;
import com.sparta.plusweek.domain.repository.PostRepository;
import com.sparta.plusweek.global.error.exception.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 댓글 서비스
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 댓글 작성
    public CommentResponseDto createComment(Long postId, CommentCreateRequestDto requestDto, User user) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 게시글 아이디"));

        Comment comment = new Comment(requestDto.getContent(), user, post);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    // 댓글 수정
    public CommentResponseDto editComment(Long commentId, CommentEditRequestDto requestDto, User user) {

        Comment comment = getRealUserComment(commentId, user);
        comment.update(requestDto);

        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    public void deleteComment(Long commentId, User user) {

        Comment comment = getRealUserComment(commentId, user);
        commentRepository.delete(comment);
    }

    // 작성자의 댓글 가져오기
    private Comment getRealUserComment(Long commentId, User user) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 댓글 아이디"));

        accessCheck(user, comment);

        return comment;
    }

    private void accessCheck(User user, Comment comment) {
        if (!user.equals(comment.getAuthor())) {
            throw new AccessDeniedException();
        }
    }
}
