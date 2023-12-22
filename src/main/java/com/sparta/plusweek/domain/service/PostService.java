package com.sparta.plusweek.domain.service;

import com.sparta.plusweek.domain.dto.*;
import com.sparta.plusweek.domain.dto.mapper.PostMapper;
import com.sparta.plusweek.domain.entity.Post;
import com.sparta.plusweek.domain.repository.PostRepository;
import com.sparta.plusweek.global.error.exception.InvalidPasswordException;
import com.sparta.plusweek.global.error.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// 게시글 서비스
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;

    public List<PostPreviewResponseDto> getPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(postMapper::toPostPreviewResponseDto)
                .toList();
    }

    public PostDetailResponseDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);

        return postMapper.toPostDetailResponseDto(post);
    }

    @Transactional
    public PostCreateResponseDto createPost(PostCreateRequestDto request) {
        passwordEncoding(request);

        Post createdPost = Post.create(request);
        Post savedPost = postRepository.save(createdPost);

        return new PostCreateResponseDto(savedPost.getId());
    }

    private void passwordEncoding(PostCreateRequestDto request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        request.changeEncodedPassword(encodedPassword);
    }

    @Transactional
    public PostDetailResponseDto editPost(Long postId, PostEditRequestDto request, String password) {
        Post post = getValidatedPost(postId, password);
        post.edit(request);

        return postMapper.toPostDetailResponseDto(post);
    }

    @Transactional
    public void delete(Long postId, String password) {
        Post post = getValidatedPost(postId, password);

        postRepository.delete(post);
    }

    private Post getValidatedPost(Long postId, String password) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        validatePassword(post, password);

        return post;
    }

    private void validatePassword(Post post, String password) {
        if (!passwordEncoder.matches(password, post.getPassword())) {
            throw new InvalidPasswordException();
        }
    }
}
