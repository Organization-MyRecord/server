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

    @ApiModelProperty(example = "게시물 id")
    private Long postId;

    @ApiModelProperty(example = "게시물 내용")
    private String content;

    @ApiModelProperty(example = "새로운 게시물 제목")
    private String newPostName;



}
