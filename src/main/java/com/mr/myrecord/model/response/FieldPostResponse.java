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
public class FieldPostResponse {
    private List<PostResponse> myPostList = new ArrayList<>();

    private Pagination postPagination;
}
