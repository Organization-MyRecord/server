package com.mr.myrecord.model.response;

import com.mr.myrecord.page.Pagination;
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

    private Long userPostId;

    private String postName;

    private String postImage;

    private String content;

    private String classification;

}
