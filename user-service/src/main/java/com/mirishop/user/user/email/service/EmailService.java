package com.mirishop.user.user.email.service;

import com.mirishop.user.user.email.repository.EmailRequest;

public interface EmailService {

    void authEmail(EmailRequest request);

    boolean verityEmail(String email, String verificationCode);
}
