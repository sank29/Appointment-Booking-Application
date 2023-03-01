package com.sanket.entity;

public class LoginDTO {
	
		
		private String mobileNo;
		private String password;
		
		public LoginDTO() {
			
		}

		public LoginDTO(String mobileNo, String password) {
			this.mobileNo = mobileNo;
			this.password = password;
		}

		public String getMobileNo() {
			return mobileNo;
		}

		public void setMobileNo(String mobileNo) {
			this.mobileNo = mobileNo;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		
		

	


}
