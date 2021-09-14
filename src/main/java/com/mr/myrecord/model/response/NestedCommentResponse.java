package com.mr.myrecord.model.response;

import com.mr.myrecord.model.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NestedCommentResponse {

    private String userEmail;

    private String userImage;

    private String comment;

    private Long postId;

    private Long parentCommendId;

    private LocalDateTime commentTime;

    private List<Comment> commentList = new ArrayList<>();

}
