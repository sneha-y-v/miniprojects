package com.sneha.service;
import java.util.Random;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    
	public String genaratePassword(int length) {
		String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";
		String specialCharacters = "!@#$%&*";
		String password = "";
		String passwordCharacters=letters+numbers+specialCharacters;
		Random random = new Random();		
		char[] pwd = new char[length];
		for(int i=0; i<length; i++) {
			pwd[i]=passwordCharacters.charAt(random.nextInt(passwordCharacters.length()));
		    password+=pwd[i];
		}
		return password;
		
	}

    public void sendMail(String toEmail, String subject, String message) {

        var mailMessage = new SimpleMailMessage();

        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailMessage.setFrom("snehayvudupi@gmail.com");

        javaMailSender.send(mailMessage);
    }
}