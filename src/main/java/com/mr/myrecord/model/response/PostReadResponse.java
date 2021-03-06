package com.mr.myrecord.model.response;

import com.mr.myrecord.model.entity.Comment;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class PostReadResponse {
    private Long id;

    @ApiModelProperty(example = "디렉토리 제목")
    private String directoryName;

    @ApiModelProperty(example = "게시물 제목")
    private String postName;

    @ApiModelProperty(example = "게시물 주인 email")
    private String postUserEmail;

    @ApiModelProperty(example = "게시물 내용")
    private String content;

    @ApiModelProperty(example = "남")
    private String classification;

    @ApiModelProperty(example = "10000")
    private Long views;

    @ApiModelProperty(example = "게시물 이미지")
    private String postImage;

}
