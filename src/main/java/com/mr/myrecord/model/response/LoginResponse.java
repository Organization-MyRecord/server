package com.mr.myrecord.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    @ApiModelProperty(name = "JWT토큰")
    private String token;

    @ApiModelProperty(name = "로그인 사용자 이메일")
    private String email;

    @ApiModelProperty(name = "로그인 사용자 이미지")
    private String image;

}
