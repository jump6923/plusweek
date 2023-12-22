package com.sparta.plusweek.domain.dto.mapper;

import com.sparta.plusweek.domain.dto.PostDetailResponseDto;
import com.sparta.plusweek.domain.dto.PostPreviewResponseDto;
import com.sparta.plusweek.domain.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

// Post 엔티티를 DTO 로 변환해주는 mapper
@Mapper(
        componentModel = "spring", // 빈으로 등록해줌
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PostMapper {
    PostDetailResponseDto toPostDetailResponseDto(Post post);
    PostPreviewResponseDto toPostPreviewResponseDto(Post post);
}