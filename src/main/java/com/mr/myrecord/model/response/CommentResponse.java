package com.mr.myrecord.model.response;

import com.mr.myrecord.model.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {

    private String userEmail;

    private String userImage;

    private String comment;

    private Long postId;

    private List<Comment> commentList = new ArrayList<>();

    private Long parentCommendId;

}
