package com.sparta.plusweek.domain.repository;

import com.sparta.plusweek.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
