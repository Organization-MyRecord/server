package com.mr.myrecord.service;

import com.mr.myrecord.model.entity.User;
import com.mr.myrecord.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 메일 발송 메소드
     */
    public String sendEmail(String email) throws UnsupportedEncodingException, MessagingException {
        String senderAddress = "wlrhkd49";
        String senderName = "MyRecord";
        String subject = " Verify your registeration";
        String content = "Dear [[name]],<br>" //메일내용
                + "Please input the Code below to verify your registration:<br>"
                + "<h3>Code = [[code]]</h3>"
                + "Thank you,<br>"
                + "MyRecord";

        // 메일 전송을 위해 필요한 객체
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        helper.setFrom(senderAddress, senderName); // sender 주소, 이름
        helper.setTo(email); // receiver 주소
        helper.setSubject(subject); // mail 제목

        // 랜덤코드 생성 (6 자리)
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        int num = 0;

        while(buffer.length() < 6) {
            num = random.nextInt(10);
            buffer.append(num);
        }
        String randomCode = buffer.toString();

        // html 내용 replace
        content = content.replace("[[name]]", email);
        content = content.replace("[[code]]", randomCode);

        //본문 담기, true는 html 형식으로 보내겠다는 의미
        helper.setText(content, true);

        javaMailSender.send(message); // 메일 보내기

        return randomCode;

    }

    public void sendTempPwEmail(String email) throws UnsupportedEncodingException, MessagingException {
        User user = userRepository.findByEmail(email);
        String senderAddress = "wlrhkd49";
        String senderName = "MyRecord";
        String subject = " MyRecord 새로운 임시 비밀번호입니다.";
        String content = "Dear [[name]],<br>" //메일내용
                + "MyRecord 임시 비밀번호를 발송했습니다.<br>"
                + "<h3>임시 비밀번호 = [[code]]</h3>"
                + "감사합니다,<br>"
                + "MyRecord";

        // 메일 전송을 위해 필요한 객체
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        helper.setFrom(senderAddress, senderName); // sender 주소, 이름
        helper.setTo(email); // receiver 주소
        helper.setSubject(subject); // mail 제목

        // 랜덤코드 생성 (6 자리)
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        int num = 0;

        while(buffer.length() < 6) {
            num = random.nextInt(10);
            buffer.append(num);
        }
        String randomCode = buffer.toString();
        user.setPassword(passwordEncoder.encode(randomCode));


        // html 내용 replace
        content = content.replace("[[name]]", email);
        content = content.replace("[[code]]", randomCode);

        //본문 담기, true는 html 형식으로 보내겠다는 의미
        helper.setText(content, true);

        javaMailSender.send(message); // 메일 보내기

    }
}
