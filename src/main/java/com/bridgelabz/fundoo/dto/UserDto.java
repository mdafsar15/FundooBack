package com.bridgelabz.fundoo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

	private String firstName;

	private String lastName;
	@Email
	private String emailId;
	
	@NotNull
	private String mobileNumber;
	
	private String password;
	
	@NotNull
	private String reTypePassword;

}