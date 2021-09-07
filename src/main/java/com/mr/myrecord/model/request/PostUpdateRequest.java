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

    @ApiModelProperty(example = "디렉토리 이름")
    private String directoryName;

    @ApiModelProperty(example = "게시물 제목")
    private String postName;

    @ApiModelProperty(example = "게시물 수정 시간")
    private LocalDateTime postDate;

    @ApiModelProperty(example = "게시물 내용")
    private String content;

    @ApiModelProperty(example = "새로운 게시물 제목")
    private String newPostName;


}
