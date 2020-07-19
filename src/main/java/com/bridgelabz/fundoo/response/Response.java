package com.bridgelabz.fundoo.response;

import java.util.List;

public class Response {

	private String message;

	private int statusCode;

	private Object object;

	private List<Object> details;

	public Response(String message, int statusCode) {
		
		this.message = message;
		this.statusCode = statusCode;
	}

	public Response(String message, int statusCode, Object object) {
		
		this.message = message;
		this.statusCode = statusCode;
		this.object = object;
	}

	

	



	



	public Response(String message, int statusCode, List<Object> details) {
	
		this.message = message;
		this.statusCode = statusCode;
		this.details = details;
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

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public List<Object> getDetails() {
		return details;
	}

	public void setDetails(List<Object> details) {
		this.details = details;
	}



	@Override
	public String toString() {
		return "Response [message=" + message + ", statusCode=" + statusCode + ", object=" + object + ", details="
				+ details + "]";
	}



		

	
	
}