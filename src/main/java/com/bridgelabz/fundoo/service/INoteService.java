package com.bridgelabz.fundoo.service;

import java.util.List;

import com.bridgelabz.fundoo.dto.NoteDto;
import com.bridgelabz.fundoo.dto.ReminderDto;
import com.bridgelabz.fundoo.dto.UpdateNoteDto;
import com.bridgelabz.fundoo.model.Note;
//import com.bridgelabz.fundoo.util.NoteData;

public interface INoteService {

	public boolean createNote(NoteDto noteDto, String token);

	public boolean deleteNote(long noteId, String token);

	public boolean updateNote(UpdateNoteDto noteDto, String token);

	public List<Note> getallNotes(String token);

	public boolean archiveNote(long noteId, String token);

	public boolean pinNote(long noteId, String token);

	public void changeColour(String token, long noteId, String noteColor);

	public boolean trashNote(long noteId, String token);

//	public void addReminder(String token, long noteId, ReminderDto remainderDTO);
//	public boolean addReminder(String token, Long notesId, ReminderDto reminder);
//	
//	public void deleteReminder(String token, long noteId);
	boolean addReminder(String token, Long noteId, ReminderDto reminder);

	boolean removeReminder(String token, Long noteId);
	
	//public boolean Note(String token, Long noteid);
	
	public List<Note> getTrashed(String token);
	
	public List<Note> getPinned(String token);

	List<Note> getArchived(String token);

	List<Note> getAllReminderNotes(String token);

	boolean restoreNote(long noteId, String token);

	public long findId(String token, String name);

	//List<Note> searchAllNotes(String searchItem);


}