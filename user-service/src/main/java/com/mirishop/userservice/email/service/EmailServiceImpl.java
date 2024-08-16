package com.mirishop.userservice.email.service;

import com.mirishop.userservice.common.redis.service.RedisService;
import com.mirishop.userservice.email.dto.EmailRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    // 싱글톤 Random을 생성하여 무작위성 보장
    private static final Random random = new Random();

    private final JavaMailSender javaMailSender;
    private final RedisService redisService;

    /**
     * 이메일 값을 받아 랜덤 인증번호를 저장하는 메소드
     */
    @Override
    @Transactional
    public void authEmail(EmailRequest request) {
        String authKey = String.valueOf(random.nextInt(888888) + 111111);

        sendAuthEmail(request.getEmail(), authKey);
    }

    /**
     * 이메일과 인증번호를 받아 검증하는 메소드
     */
    @Override
    @Transactional(readOnly = true)
    public boolean verityEmail(String email, String verificationCode) {
        String storedEmail = redisService.getValue(verificationCode);
        return email.equals(storedEmail);
    }

    /**
     * 메일 인증코드를 보내는 메소드
     */
    private void sendAuthEmail(String email, String authKey) {
        String subject = "메일 인증코드";
        String text = "회원 가입을 위한 인증번호는 " + authKey + "입니다. <br/>";

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        // 유효 시간(5분)동안 {email, authKey} 저장
        redisService.setDataExpire(authKey, email, 60 * 5L);
    }
}
