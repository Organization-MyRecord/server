package com.mr.myrecord.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MainPageResponse {

    @ApiModelProperty(example = "인기있는 게시물 리스트")
    List<PopularPostResponse> popularPostResponseList;

    @ApiModelProperty(example = "나의 최근 게시물 리스트")
    List<RecentMyPostResponse> recentMyPostResponseList;

    @ApiModelProperty(example = "모든 게시물 중에 최근 게시물 리스트")
    List<RecentEveryPostResponse> recentEveryPostResponseList;
}
