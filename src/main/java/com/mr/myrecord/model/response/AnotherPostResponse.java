package com.mr.myrecord.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AnotherPostResponse {

    @ApiModelProperty(value = "이전에 쓴 글 차례대로 2 개")
    List<PostUnderResponse> postUnderResponseList;
    @ApiModelProperty(value = "이후에 쓴 글 차례대로 2 개")
    List<PostUpperResponse> postUpperResponseList;

}
