package com.bridgelabz.fundoo.dto;

import javax.validation.constraints.Email;

import lombok.Data;

@Data
public class LoginDto {

	@Email
	private String email;
	private String password;

}
