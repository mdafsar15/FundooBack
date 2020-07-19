package com.bridgelabz.fundoo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.exception.AuthorizationException;
import com.bridgelabz.fundoo.exception.CollaboratorException;
import com.bridgelabz.fundoo.exception.NoteException;
import com.bridgelabz.fundoo.exception.UserDoesNotExistException;
import com.bridgelabz.fundoo.model.Note;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.INoteRepository;
import com.bridgelabz.fundoo.repository.IUserRepository;
import com.bridgelabz.fundoo.repository.NoteRepository;
import com.bridgelabz.fundoo.repository.UserRepository;
import com.bridgelabz.fundoo.util.JwtGenerator;


@Service
public class CollaboratorService implements ICollaboratorService {
	
	
	
	@Autowired
	private JwtGenerator generateToken;

	private User user = new User();

	@Autowired
	private UserRepository repository;

	@Autowired
	private IUserRepository usersRepository;

	@Autowired
	private NoteRepository notesRepository;
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Transactional
	public Note addCollaborator(Long noteId, String email, String token) {
		User user;

		User collaborator = repository.getUser(email);
		try {
			Long userId = JwtGenerator.decodeToken(token);
			user = repository.getUser(userId);
		} catch (Exception e) {
			throw new CollaboratorException("USER NOT FOUND WITH GIVEN Id", 404);
		}
		if (user != null) {
			if (collaborator != null) {
				Note note = notesRepository.findBynotesId(noteId);
				collaborator.getCollaboratedNotes().add(note);
				usersRepository.save(collaborator);
				notesRepository.saveOrUpdate(note);
				SimpleMailMessage mailMessage = new SimpleMailMessage();
				mailMessage.setTo(email);
				mailMessage.setFrom("mdafsaransari720@gmail.com");
				mailMessage.setSubject("Collabrate Successfully");
				mailMessage.setText("collabrator link " + " http://localhost:4200/dashboard/notes");
				javaMailSender.send(mailMessage);
				return note;
			} else {
				throw new CollaboratorException("Collaborator is null", 401);
			}
		} else {
			throw new UserDoesNotExistException("user not found");
		}
	}

//	@Transactional
//	public NotesEntity removeCollaborator(Long notesId, String email, String token) {
//		UsersEntity user;
//
//		UsersEntity collaborator = repository.getusersByemail(email);
//		try {
//			Long userId = generateToken.parseJWTToken(token);
//			user = repository.getUser(userId);
//		} catch (Exception e) {
//			throw new UserNotFoundException("USER NOT FOUND WITH GIVEN Id", HttpStatus.NOT_FOUND);
//		}
//		if (user != null) {
//			if (collaborator != null) {
//				NotesEntity note = notesRepository.findBynotesId(notesId);
//				note.getCollaborateUser().remove(collaborator);
//				notesRepository.createNote(note);
//			} else {
//				throw new CollaboratorNotFoundExcepton("there is no collaborator", HttpStatus.NOT_FOUND);
//			}
//
//		}
//		return null;
//	}
//
//	@Transactional
//	public List<NotesEntity> getAllNotesCollaborators(String token) {
//		try {
//			Long userId = generateToken.parseJWTToken(token);
//			user = repository.getUser(userId);
//		} catch (Exception e) {
//			throw new UserNotFoundException("USER NOT FOUND WITH GIVEN Id", HttpStatus.NOT_FOUND);
//		}
//		List<NotesEntity> notes = user.getCollaborateNotes();
//		return notes;
//	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	@Autowired
//	private IUserRepository uRepo;
//	@Autowired
//	private INoteRepository nRepo;
//               
//	private User authenticatedMainUser(String token) {
//		User user = uRepo.getUser(JwtGenerator.decodeToken(token));
//		if (user != null) {
//			return user;
//		}
//		throw new AuthorizationException("Authorization failed", 401);
//	}
//
//	private Note verifiedNote(long noteId) {
//		Note note = nRepo.getNote(noteId);
//		if (note != null) {
//			if (!note.isTrashed()) {
//				return note;
//			}
//			throw new NoteException("Note is trashed", 300);
//		}
//		throw new NoteException("Note not found", 300);
//	}
//
//	private User validCollaborator(String emailId) {
//		User collaborator = uRepo.getUser(emailId);
//		if (collaborator != null && collaborator.isVerified()) {
//			return collaborator;
//		}
//		throw new CollaboratorException("Collaborator is not a valid user", 404);
//	}
//
//	@Override
//	public boolean addCollaborator(String token, long noteId, String emailId) {
//		authenticatedMainUser(token);
//		Note validNote = verifiedNote(noteId);
//		User validCollaborator = validCollaborator(emailId);
//		validNote.getCollaboratedUsers().add(validCollaborator);
//		validCollaborator.getCollaboratedNotes().add(validNote);
//		nRepo.saveOrUpdate(validNote);
//		return true;
//	}

}