package com.bridgelabz.fundoo.exception;

public class NoteException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final int status;

	public NoteException(String message, int status) {
		super(message);
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

}