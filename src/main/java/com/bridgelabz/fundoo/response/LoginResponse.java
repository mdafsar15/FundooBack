package com.bridgelabz.fundoo.response;

import com.bridgelabz.fundoo.dto.LoginDto;

import lombok.Data;

@Data
public class LoginResponse {

	private String message;
	private int statusCode;
	private String token;
	private String firstName;
	public LoginResponse(String message, int statusCode, String token, String firstName) {
		super();
		this.message = message;
		this.statusCode = statusCode;
		this.token = token;
		this.firstName = firstName;
	}
	public LoginResponse(String message, int statusCode) {

		this.message = message;
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@Override
	public String toString() {
		return "LoginResponse [message=" + message + ", statusCode=" + statusCode + ", token=" + token + ", firstName="
				+ firstName + "]";
	}

}
