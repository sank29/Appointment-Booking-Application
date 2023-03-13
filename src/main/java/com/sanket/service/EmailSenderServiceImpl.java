package com.sanket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;

@Service
public class EmailSenderServiceImpl implements EmailSenderService{
	
	@Autowired
	private JavaMailSender javaMailSender; 

	@Override
	public Boolean sendAppointmentBookingDoneMail(String toEmail,String subject, String body ) throws MessagingException {
		
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		
		simpleMailMessage.setFrom("wankhedes29@gmail.com");
		
		simpleMailMessage.setTo(toEmail);
		simpleMailMessage.setText(body);
		simpleMailMessage.setSubject(subject);
		
		javaMailSender.send(simpleMailMessage);
		
		
		return true;
	
	}

}
