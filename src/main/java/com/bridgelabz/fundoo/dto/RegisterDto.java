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
 
public class RegisterDto {

	private String fname;
	private String lname;
	@Email
	private String email;

	private String mob_number;
	
	private String password;
	
	@NotNull
	private String reTypePassword;

}
