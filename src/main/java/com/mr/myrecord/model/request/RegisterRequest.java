package com.mr.myrecord.model.request;
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

    private GenderEnum gender;

    private String field;

    private String birth;

    private String major;

    private String detailMajor;

    private int age;

    private String email;

    private String randomCode;

    private Boolean verification;

}
