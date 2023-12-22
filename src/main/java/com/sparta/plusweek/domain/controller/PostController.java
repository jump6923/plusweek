package com.sparta.plusweek.domain.controller;

import com.sparta.plusweek.domain.dto.*;
import com.sparta.plusweek.domain.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("")
    public ResponseEntity<List<PostPreviewResponseDto>> getPosts() {
        List<PostPreviewResponseDto> posts = postService.getPosts();

        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponseDto> getPost(@PathVariable Long postId) {
        PostDetailResponseDto postDetail = postService.getPost(postId);

        return ResponseEntity.ok(postDetail);
    }

    @PostMapping("")
    public ResponseEntity<PostCreateResponseDto> createPost(@Valid @RequestBody PostCreateRequestDto request) {
        PostCreateResponseDto createPostDto = postService.createPost(request);

        return ResponseEntity.ok(createPostDto);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<PostDetailResponseDto> editPost(
            @PathVariable Long postId,
            @Valid @RequestBody PostEditRequestDto request,
            @RequestHeader String password) {

        PostDetailResponseDto editedPostDetail = postService.editPost(postId, request, password);

        return ResponseEntity.ok(editedPostDetail);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId, @RequestHeader("password") String password) {
        postService.delete(postId, password);

        return ResponseEntity.noContent().build();
    }
}