package com.mr.myrecord.controller;

import com.mr.myrecord.model.entity.User;
import com.mr.myrecord.model.request.RegisterRequest;
import com.mr.myrecord.service.MailService;
import com.mr.myrecord.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

@RestController
@Api(tags = "이메일 인증 & 가입")
public class RegisterController {

    @Autowired
    private MailService mailService;

    @Autowired
    UserService userService;

    @ApiOperation(value =  "이메일로 랜덤 코드 전송")
    @GetMapping("/email")
    public String email(
            @ApiParam(value = "이메일 주소", required = true, example = "test@naver.com")
            @RequestParam String email,
                        HttpSession httpSession) throws UnsupportedEncodingException, MessagingException {

        RegisterRequest body = new RegisterRequest();

        if (!userService.emailCheck(email)) {
            return "이미 존재하는 Email";
        }

        body.setEmail(email);

        /**
         * 메일 보내고 인증코드 받기
         */
        String randomCode = mailService.sendEmail(email);

        body.setRandomCode(randomCode);

        // 세션에 받은 이메일을 key로 request객체 Session에 저장
        // name, value 쌍으로 값 저장
        httpSession.setAttribute(body.getEmail(), body);

        return "email code 전송";

    }

    /**
     * 랜덤코드, 이메일 인증후 받으면 true
     * 클라이언트에선 이메일로 인증코드 받은 경우 verify 신청 가능하게 함
     */
    @ApiOperation(value =  "랜덤 코드 올바른지 확인")
    @GetMapping("/verify")
    public boolean verify(
            @ApiParam(value = "!!이메일 주소 필수!!", required = true, example = "test@naver.com")
            @RequestParam String email,
                         @ApiParam(value = "!!랜덤코드 입력 필수!!", required = true, example = "******")
                         @RequestParam String randomCode,
                         HttpSession httpSession) {
        RegisterRequest body = RegisterRequest.builder()
                .email(email)
                .randomCode(randomCode)
                .build();

        //쿠키의 세션을 받아 파라미터로 받은 이메일에 해당하는 User꺼내고
        //해당 User와 파라미터로 받은 request의 randomCode비교
        RegisterRequest oldBody = (RegisterRequest) httpSession.getAttribute(body.getEmail());

        //쿠키 없으면 이메일 인증 필요
        if (oldBody == null) {
            //return "이메일 인증 필요";
            return false;
        }

        //randomCode가 같으면
        if (body.getRandomCode().contains(oldBody.getRandomCode())) {
            oldBody.setVerification(true);
            httpSession.setAttribute(oldBody.getEmail(), oldBody);
            return true;
            // return "이메일 인증 완료";
        }

        //randomCode 다르면
        else {
            return false;
            //return "인증번호가 올바르지 않습니다.";
        }
    }

    /**
     * 인증 완료 후 회원가입 진행
     * 세션에서 꺼내서 확인하고 저장
     * 필수 정보 : 이름, 직업, 성별, 학과, 나이, 이메일, 비밀번호
     */
    @ApiOperation(value = "회원가입 정보 입력 후 확인 버튼", notes = "회원가입 객체 전달 필요")
    @PostMapping("/register")
    public String create(@RequestBody RegisterRequest request,
                         HttpSession httpSession) {
        RegisterRequest oldBody = (RegisterRequest) httpSession.getAttribute(request.getEmail());

        // 세션에서 꺼낸 User가 인증된 경우
        if (oldBody.getVerification()) {
            if (!request.getPassword().equals(request.getSecondPassword())) {
                return "비밀번호 확인 오류";
            }

            if (request.getEmail() == null) {
                return "이메일 정보가 틀렸습니다";
            }

            User user = userService.create(request);

            // 세션만료
            httpSession.removeAttribute(oldBody.getEmail());

            return "가입  완료";

        }
        else {
            return "인증 안된 사용자 입니다.";
        }
    }
}
