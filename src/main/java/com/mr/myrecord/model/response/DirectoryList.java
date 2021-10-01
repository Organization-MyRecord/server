package com.mr.myrecord.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DirectoryList {
    @ApiModelProperty(example = "사용자 디렉토리 이름")
    String directoryName;

    @ApiModelProperty(example = "디렉토리 안의 게시물 개수")
    Long count;
}
