package com.mirishop.userservice.infrastructure.email.service;

import com.mirishop.userservice.infrastructure.email.dto.EmailRequest;

public interface EmailService {

    void authEmail(EmailRequest request);

    boolean verityEmail(String email, String verificationCode);
}
