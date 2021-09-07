package com.mr.myrecord.model.response;

import com.mr.myrecord.page.Pagination;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class PostResponse {

    private Long id;

    @ApiModelProperty(example = "포스트 주인 id")
    private Long userPostId;

    @ApiModelProperty(example = "게시물 제목")
    private String postName;

    @ApiModelProperty(example = "게시물 주인 email")
    private String postUserEmail;

    @ApiModelProperty(example = "게시물 이미지")
    private String postImage;

    @ApiModelProperty(example = "게시물 내용")
    private String content;

    @ApiModelProperty(example = "남")
    private String classification;

    @ApiModelProperty(example = "10000")
    private Long views;

}
