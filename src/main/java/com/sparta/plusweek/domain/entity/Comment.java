package com.sparta.plusweek.domain.entity;

import com.sparta.plusweek.domain.dto.CommentEditRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post Post;

    public Comment(String content, User author, Post post) {
        this.content = content;
        this.author = author;
        this.Post = post;
    }

    public void update(CommentEditRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
