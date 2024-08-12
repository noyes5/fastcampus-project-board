package com.mirishop.user.email.service;

import com.mirishop.user.email.dto.EmailRequest;

public interface EmailService {

    void authEmail(EmailRequest request);

    boolean verityEmail(String email, String verificationCode);
}
