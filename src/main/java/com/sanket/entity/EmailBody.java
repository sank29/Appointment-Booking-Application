package com.sanket.entity;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class EmailBody {
	
	String emailSubject;
	String emailBody;
	

}
