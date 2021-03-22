package com.cmc.invitaservice.mailsender;
public interface EmailService {
    void sendEmail(String from, String to, String subject, String body);
}
