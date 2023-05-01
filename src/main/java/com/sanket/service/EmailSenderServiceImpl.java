package com.sanket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.sanket.entity.EmailBody;

import jakarta.mail.MessagingException;

@Service
public class EmailSenderServiceImpl implements EmailSenderService{
	
	@Autowired
	private JavaMailSender javaMailSender; 

	@Override
	public Boolean sendAppointmentBookingMail(String toEmail, EmailBody emailBody) throws MessagingException {
		
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		
		simpleMailMessage.setFrom("sanket98wankhede@gmail.com");
		
		simpleMailMessage.setTo(toEmail);
		simpleMailMessage.setText(emailBody.getEmailBody());
		simpleMailMessage.setSubject(emailBody.getEmailSubject());
		
		javaMailSender.send(simpleMailMessage);
		
		
		return true;
	
	}

}
