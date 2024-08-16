package com.mirishop.userservice.email.service;

import com.mirishop.userservice.email.dto.EmailRequest;

public interface EmailService {

    void authEmail(EmailRequest request);

    boolean verityEmail(String email, String verificationCode);
}
