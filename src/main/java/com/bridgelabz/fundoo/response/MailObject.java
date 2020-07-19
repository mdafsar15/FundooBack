package com.bridgelabz.fundoo.response;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class MailObject implements Serializable {

	private static final long serialVersionUID = 1L;

	String email;

	String subject;

	String message;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}