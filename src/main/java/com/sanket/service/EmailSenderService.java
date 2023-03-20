package com.sanket.service;

import com.sanket.entity.EmailBody;

import jakarta.mail.MessagingException;

public interface EmailSenderService {
	
	public Boolean sendAppointmentBookingMail(String toEmail, EmailBody emailBody) throws MessagingException;
	

}
