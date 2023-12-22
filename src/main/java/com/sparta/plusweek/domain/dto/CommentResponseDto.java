package com.sparta.plusweek.domain.dto;

import com.sparta.plusweek.domain.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private final Long commentId;
    private final String content;
    private final String username;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.username = comment.getAuthor().getUsername();
    }
}
