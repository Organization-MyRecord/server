package com.mr.myrecord.model.request;

import com.mr.myrecord.model.entity.FieldEnum;
import com.mr.myrecord.model.entity.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    private String password;

    private String secondPassword;

    private String name;

    private String job;

    private GenderEnum gender;

    private FieldEnum field;

    private String major;

    private int age;

    private String email;

    private String randomCode;

    private Boolean verification;

}
