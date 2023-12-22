package com.sparta.plusweek.domain.entity;

import com.sparta.plusweek.domain.dto.PostCreateRequestDto;
import com.sparta.plusweek.domain.dto.PostEditRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 게시글 엔티티.
@Getter
@Entity
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false,length = 5000)
    private String contents;

    private Post(String title, String author, String password, String contents) {
        this.title = title;
        this.author = author;
        this.password = password;
        this.contents = contents;
    }

    public static Post create(PostCreateRequestDto request) {
        return new Post(request.getTitle(), request.getAuthor(), request.getPassword(), request.getContents());
    }

    public void edit(PostEditRequestDto request) {
        this.title = request.getTitle();
        this.author = request.getAuthor();
        this.contents = request.getContents();
    }
}
