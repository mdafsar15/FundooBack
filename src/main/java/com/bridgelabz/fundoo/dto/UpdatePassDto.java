package com.bridgelabz.fundoo.dto;

import lombok.Data;

@Data
public class UpdatePassDto {

	private String emailId;
	private String password;
	private String confirmPassword;

}