package com.mr.myrecord.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;

    private String password;

    private String image;

    private String name;

    private String job;

    private String major;

    private String fieid;

    private int age;

    private String content;

    private int reporterCount = 3;

    private int reportedCount = 3;
}
