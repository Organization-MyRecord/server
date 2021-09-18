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
public class DirectoryListResponse {

    @ApiModelProperty(example = "나의 디렉토리 리스트")
    private List<String> directoryList;

}
