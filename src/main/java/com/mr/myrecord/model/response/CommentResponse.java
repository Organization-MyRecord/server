package com.mr.myrecord.model.response;

import com.mr.myrecord.model.entity.Comment;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {

    @ApiModelProperty(example = "유저 이름")
    private String userName;

    @ApiModelProperty(example = "유저 이미지")
    private String userImage;

    @ApiModelProperty(example = "댓글 내용")
    private String comment;

    @ApiModelProperty(example = "대댓글 상위")
    private Long parentCommendId;

    @ApiModelProperty(example = "댓글 등록 시간")
    private LocalDateTime commentTime;

    @ApiModelProperty(example = "대댓글 리스트")
    private List<NestedCommentResponse> commentList = new ArrayList<>();


}
