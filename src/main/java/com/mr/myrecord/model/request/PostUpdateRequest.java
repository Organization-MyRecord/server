package com.mr.myrecord.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostUpdateRequest {

    @ApiModelProperty(example = "게시물 ID")
    private Long postId;

    @ApiModelProperty(example = "게시물 내용")
    private String content;

    @ApiModelProperty(example = "새로운 게시물 제목 or 수정하지 않으면 기존 게시물 제목")
    private String newPostName;

    @ApiModelProperty(example = "수정할 디렉토리 이름")
    private String directoryName;

}
