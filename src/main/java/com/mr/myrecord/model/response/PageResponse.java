package com.mr.myrecord.model.response;

import com.mr.myrecord.model.entity.Directory;
import com.mr.myrecord.page.Pagination;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse {

    @ApiModelProperty(example = "이미지")
    private String image;

    @ApiModelProperty(example = "닉네임")
    private String name;

    @ApiModelProperty(example = "계열")
    private String major;

    @ApiModelProperty(example = "세부전공")
    private String detailMajor;

    @ApiModelProperty(example = "IT웹통신")
    private String field;

    private int age;

    @ApiModelProperty(example = "자기소개")
    private String content;

    @ApiModelProperty(example = "자기 이메일")
    private String email;

    @ApiModelProperty(example = "팔로워 수")
    private Long favoriteUserNum;

    @ApiModelProperty(example = "자신의 게시물 개수")
    private Long postNum;

    private List<PostResponse> myPostList = new ArrayList<>();

    private List<DirectoryResponse> directoryList = new ArrayList<>();

    private Pagination postPagination;

}
