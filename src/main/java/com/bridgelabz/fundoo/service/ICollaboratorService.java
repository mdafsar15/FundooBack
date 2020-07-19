package com.bridgelabz.fundoo.service;

import java.util.List;

import com.bridgelabz.fundoo.model.Note;


public interface ICollaboratorService {

	//boolean add Collaborator(String token, long noteId, String emailId);
	
	Note addCollaborator(Long noteId,String email,String token);
	
	//Note removeCollaborator(Long notesId,String email,String token);
	
	//List<Note> getAllNotesCollaborators(String token);

}
