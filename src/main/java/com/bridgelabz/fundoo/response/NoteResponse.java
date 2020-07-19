package com.bridgelabz.fundoo.response;

import java.util.List;

import com.bridgelabz.fundoo.dto.NoteDto;
import com.bridgelabz.fundoo.model.Note;

import lombok.Data;
import lombok.ToString;
@Data
@ToString
public class NoteResponse {

	private NoteDto note;
	private String message;

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

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	private int statusCode;
	private List<Note> notes;

	public NoteResponse(String message, int statusCode, List<Note> notes) {
		super();
		this.message = message;
		this.statusCode = statusCode;
		this.notes = notes;
	}

	public NoteResponse(NoteDto note) {
		this.setNote(note);
	}

	public NoteDto getNote() {
		return note;
	}

	public void setNote(NoteDto note) {
		this.note = note;
	}
}