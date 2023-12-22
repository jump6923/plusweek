package com.sparta.plusweek.domain.controller;

import com.sparta.plusweek.domain.dto.CommentCreateRequestDto;
import com.sparta.plusweek.domain.dto.CommentEditRequestDto;
import com.sparta.plusweek.domain.dto.CommentResponseDto;
import com.sparta.plusweek.domain.dto.CustomUserDetails;
import com.sparta.plusweek.domain.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long postId,
            @RequestBody CommentCreateRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        CommentResponseDto responseDto = commentService.createComment(postId, requestDto, userDetails.getUser());

        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> editComment(
            @PathVariable Long commentId,
            @RequestBody CommentEditRequestDto requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        CommentResponseDto responseDto = commentService.editComment(commentId, requestDto, userDetails.getUser());

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        commentService.deleteComment(commentId, userDetails.getUser());

        return ResponseEntity.ok().build();
    }
}
