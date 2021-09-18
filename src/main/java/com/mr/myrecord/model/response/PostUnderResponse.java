package com.mr.myrecord.model.response;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostUnderResponse {
    private Long postId;

    private String postName;

    private LocalDateTime postDate;
}
