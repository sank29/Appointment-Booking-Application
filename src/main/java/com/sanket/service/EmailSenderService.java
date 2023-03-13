package com.sanket.service;

import jakarta.mail.MessagingException;

public interface EmailSenderService {
	
	public Boolean sendAppointmentBookingDoneMail(String toEmail, String subject,String body) throws MessagingException;

}
