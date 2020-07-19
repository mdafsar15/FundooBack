package com.bridgelabz.fundoo.exception;

public class ReminderException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final int status;

	public ReminderException(String message, int status) {
		super(message);
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
}