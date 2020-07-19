package com.bridgelabz.fundoo.repository;

import java.util.List;

import com.bridgelabz.fundoo.model.Note;
//import com.bridgelabz.fundoo.util.NoteData;

public interface INoteRepository {

	public Note saveOrUpdate(Note newNote);

	public Note getNote(long noteId);

	public boolean deleteNote(long noteId);

	public List<Note> getAllNotes(long userId);
	public List<Note> getAllTrashed(long userId);
	public List<Note> getPinned(long userId);

	List<Note> getAllArchived(long uId);
	public List<Note> getAllReminderNotes(long uId);
	
	public long findId(long userId, String name);
	
	public Note findBynotesId(long notesid);




}