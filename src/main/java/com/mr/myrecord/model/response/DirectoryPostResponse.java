package com.mr.myrecord.model.response;

import com.mr.myrecord.page.Pagination;
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
public class DirectoryPostResponse {

    @ApiModelProperty(example = "게시물 리스트")
    List<PostResponse> postResponseList;

    @ApiModelProperty(example = "페이징 정보")
    Pagination pagination;
}
