package com.mirishop.userservice.infrastructure.email.service;

import com.mirishop.userservice.infrastructure.redis.RedisService;
import com.mirishop.userservice.infrastructure.email.dto.EmailRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.expiration-time}")
    private Duration emailExpirationTime;

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
    public boolean verityEmail(String email, String verificationCode) {
        String storedEmail = redisService.getValue("EMAILCODE=" + verificationCode);
        return email.equals(storedEmail);
    }

    // 인증코드가 담긴 이메일 전송 메소드
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
            log.error("Failed to send authentication email to {}. Error: {}", email, e.getMessage(), e);
        }

        // 유효 시간(5분)동안 {email, authKey} 저장
        redisService.setDataExpire("EMAILCODE=" +authKey, email, emailExpirationTime.getSeconds());
    }
}

