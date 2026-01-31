package com.example.eventflowsaas.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    @Async
    public void bookingConfirmationMessageSender(String to, String eventName, Long bookingId) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject("Your Booking Confirmation - EventFlow");

        String emailBody = String.format(
                "Hello! \n\nYour booking for the event '%s' has been confirmed. \nBooking ID: #%d \n\nThank you for using EventFlow!",
                eventName, bookingId
        );

        simpleMailMessage.setText(emailBody);
        javaMailSender.send(simpleMailMessage);
    }
}