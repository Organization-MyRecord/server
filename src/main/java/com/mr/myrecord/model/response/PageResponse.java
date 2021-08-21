package com.mr.myrecord.model.response;

import com.mr.myrecord.page.Pagination;
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

    private String image;

    private String name;

    private String major;

    private String detailMajor;

    private String field;

    private int age;

    private String content;

    private String email;

    private Long favoriteUserNum;

    private Long postNum;

    private List<PostResponse> myPostList = new ArrayList<>();

    private Pagination postPagination;

}
