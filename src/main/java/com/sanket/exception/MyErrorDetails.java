package com.sanket.exception;

import java.time.LocalDateTime;

public class MyErrorDetails {


	private String details;
	private String errorMsg;
	private LocalDateTime localDateTime;
	
	
	
	public MyErrorDetails() {
		
	}

	public MyErrorDetails(String details, String errorMsg, LocalDateTime localDateTime) {
		this.details = details;
		this.errorMsg = errorMsg;
		this.localDateTime = localDateTime;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}
}
