package com.mr.myrecord.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class PostUpdateResponse {

    @ApiModelProperty(example = "게시물 수정 시간")
    private LocalDateTime postDate;

    @ApiModelProperty(example = "게시물 내용")
    private String content;

    @ApiModelProperty(example = "새로운 게시물 제목")
    private String newPostName;
}
