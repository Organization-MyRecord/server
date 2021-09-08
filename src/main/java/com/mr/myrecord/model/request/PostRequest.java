package com.mr.myrecord.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequest {
    @ApiModelProperty(name = "게시물 이름")
    private String postName;

    @ApiModelProperty(name = "게시글 내용")
    private String content;

    @ApiModelProperty(name = "게시글 대표 이미지")
    private String postImage;

    @ApiModelProperty(name = "디렉토리 이름")
    private String directoryName;
}
