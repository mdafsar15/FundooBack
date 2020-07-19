package com.bridgelabz.fundoo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.dto.NoteDto;
import com.bridgelabz.fundoo.dto.ReminderDto;
import com.bridgelabz.fundoo.dto.UpdateNoteDto;
import com.bridgelabz.fundoo.exception.AuthorizationException;
import com.bridgelabz.fundoo.exception.NoteException;
import com.bridgelabz.fundoo.exception.ReminderException;
import com.bridgelabz.fundoo.model.Note;
import com.bridgelabz.fundoo.model.User;
import com.bridgelabz.fundoo.repository.INoteRepository;
import com.bridgelabz.fundoo.repository.IUserRepository;
import com.bridgelabz.fundoo.util.JwtGenerator;

@Service
public class Noteservice implements INoteService {

	@Autowired
	private IUserRepository urepo;
	@Autowired
	private INoteRepository nrepo;

	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	private long getRedisCacheId(String token) {

		String[] tokenSplit = token.split("\\.");
		System.out.println(tokenSplit);
		String redisToken = tokenSplit[1] + tokenSplit[2];
		System.out.println("Inside redis cache method");
		if (redisTemplate.opsForValue().get(redisToken) == null) {
			long redisId = JwtGenerator.decodeToken(token);

			redisTemplate.opsForValue().set(redisToken, redisId, 3 * 60 * 60 * 1000, TimeUnit.SECONDS);
		}
		return (Long) redisTemplate.opsForValue().get(redisToken);
	}

	private User authenticatedUser(String token) {
		User getUser = urepo.getUser(JwtGenerator.decodeToken(token));
		if (getUser != null) {
			return getUser;
		}
		throw new AuthorizationException("Authorization failed", 401);
	}

	@Override
	public boolean createNote(NoteDto noteDto, String token) {
		// found authorized user
		System.out.println("Note Dto: " + noteDto);
		User getUser = authenticatedUser(token);
		Note newNote = new Note();
		BeanUtils.copyProperties(noteDto, newNote);
		newNote.setCreatedDate(LocalDateTime.now());
		newNote.setColor("white");
		getUser.getNotes().add(newNote);
		nrepo.saveOrUpdate(newNote);
		return true;
	}

	private Note isVerified(long noteId) {

		Note getNote = nrepo.getNote(noteId);
		if (getNote != null) {
			return getNote;
		}
		throw new NoteException("Note not found", 300);
	}

	@Override
	public boolean deleteNote(long noteId, String token) {

//		noteId = getRedisCacheId(token);

		authenticatedUser(token);

		isVerified(noteId);
		nrepo.deleteNote(noteId);
		return true;
	}

	@Override
	public boolean updateNote(UpdateNoteDto noteDto, String token) {
		// found authorized user
		long noteId=getRedisCacheId(token);
		authenticatedUser(token);
		// verified valid note
		Note getNote = isVerified(noteId);
		BeanUtils.copyProperties(noteDto, getNote);

		nrepo.saveOrUpdate(getNote);
		return true;
	}

	@Override
	@Transactional
	public List<Note> getallNotes(String token) {

		// found authorized user
		User getUser = authenticatedUser(token);
		// note found
		List<Note> getNotes = nrepo.getAllNotes(getUser.getId());
		if (!getNotes.isEmpty()) {
			System.out.println("Notes: " + getNotes);
			return getNotes;
		}

		// empty list+
		return getNotes;
	}

	public boolean archiveNote(long noteId, String token) {
		// found authorized user
		authenticatedUser(token);
		// verified valid note
		Note getNote = isVerified(noteId);
		// fetched note is not archived
		if (!getNote.isArchived()) {
			getNote.setArchived(true);

			nrepo.saveOrUpdate(getNote);
			return true;
		}
		getNote.setArchived(false);
//		fetchedNote.setUpdatedDate(LocalDateTime.now());
		nrepo.saveOrUpdate(getNote);

		// if archived already
		return false;
	}

	@Override
	public boolean pinNote(long noteId, String token) {
		// found authorized user
		authenticatedUser(token);
		// verified valid note
		Note getNote = isVerified(noteId);
		if (!getNote.isPinned()) {
			getNote.setPinned(true);

			nrepo.saveOrUpdate(getNote);
			return true;
		}
		// if already pinned
		getNote.setPinned(false);

		nrepo.saveOrUpdate(getNote);
		return false;
	}

	@Override
	public void changeColour(String token, long noteId, String noteColour) {
		// authenticate user
		authenticatedUser(token);
		// validate note
		Note getNote = isVerified(noteId);
		getNote.setColor(noteColour);

		nrepo.saveOrUpdate(getNote);
	}

	@Override
	public boolean trashNote(long noteId, String token) {
		// found authorized user
		authenticatedUser(token);
		// verified valid note
		Note getNote = isVerified(noteId);
		if (!getNote.isTrashed()) {
			getNote.setTrashed(true);

			nrepo.saveOrUpdate(getNote);
			return true;
		}
		// if already trashed
		return false;
	}

//	@Override
//	public void addReminder(String token, long noteId, ReminderDto remainderDTO) {
//		// authenticate user
//		authenticatedUser(token);
//		// validate note
//		Note getNote = isVerified(noteId);
//		if (getNote.getReminderDate() == null) {
//
//			getNote.setReminderDate(remainderDTO.getRemainder());
//			nrepo.saveOrUpdate(getNote);
//			return;
//		}
//		throw new ReminderException("Reminder already set!", 502);
//	}
	

//	@Override
//	public boolean addReminder(String token, Long noteId, ReminderDto reminder) {
//		Note notes = nrepo.findBynotesId(noteId);
//		if (notes != null) {
//			notes.setReminderDate(reminder.getRemainder());
//			nrepo.saveOrUpdate(notes);
//		} else {
//			throw new ReminderException("there is no notes on userId please create a note",502);
//		}
//		return true;
//	}
//
//	@Override
//	public void deleteReminder(String token, long noteId) {
//		// authenticate user
//		authenticatedUser(token);
//		// validate note
//		Note getNote = isVerified(noteId);
//		if (getNote.getReminderDate() != null) {
//			getNote.setReminderDate(null);
//
//			nrepo.saveOrUpdate(getNote);
//			return;
//		}
//		throw new ReminderException("Reminder already removed!", 502);
//	}
	
	public boolean addReminder(String token, Long noteId, ReminderDto reminder) {
		Note notes = nrepo.findBynotesId(noteId);
		if (notes != null) {
			notes.setReminderDate(reminder.getRemainder());
			nrepo.saveOrUpdate(notes);
		} else {
			throw new NoteException("there is no notes on userId please create a note",404);
		}
		return true;
	}

	public boolean removeReminder(String token, Long noteId) {
		Note notes = nrepo.findBynotesId(noteId);
		if (notes != null) {
			notes.setReminderDate(null);
			nrepo.saveOrUpdate(notes);
		} else {
			throw new NoteException("there is no notes on userId please create a note",404);
		}
		return true;
	}
	

	@Override
	public boolean restoreNote(long noteId, String token) {
		// found authorized user
		authenticatedUser(token);
		// verified valid note
		Note fetchedNote = isVerified(noteId);
		if (fetchedNote.isTrashed()) {
			fetchedNote.setTrashed(false);
			fetchedNote.setUpdatedDate(LocalDateTime.now());
			nrepo.saveOrUpdate(fetchedNote);
//			elasticSearchRepository.updateNote(fetchedNote);
			return true;
		}
		return false;
	}

	@Override
	public List<Note> getTrashed(String token) {
		// note found of authenticated user
		List<Note> getTrashed = nrepo.getAllTrashed(authenticatedUser(token).getId());
		if (!getTrashed.isEmpty()) {
			return getTrashed;
		}
		// empty list
		return getTrashed;
	}

	@Override
	public List<Note> getArchived(String token) {
		// note found of authenticated user
		List<Note> getArchived = nrepo.getAllArchived(authenticatedUser(token).getId());
		if (!getArchived.isEmpty()) {
			return getArchived;
		}
		// empty list
		return getArchived;
	}

	@Override
	public List<Note> getAllReminderNotes(String token) {
		// note found of authenticated user
		List<Note> getReminder = nrepo.getAllReminderNotes(authenticatedUser(token).getId());
		if (!getReminder.isEmpty()) {
			return getReminder;
		}
		// empty list
		return getReminder;
	}

	@Override
	public List<Note> getPinned(String token) {

		List<Note> getPinned = nrepo.getPinned(authenticatedUser(token).getId());
//		if (!fetchedPinnedNotes.isEmpty()) {
//			return fetchedPinnedNotes;
//		}
		return getPinned;
	}
	
	@Override
	public long findId(String token, String name)
	{
		long userId=JwtGenerator.decodeToken(token);
		return nrepo.findId(userId, name);
	}
	
//	@Override
//	public List<Note> searchAllNotes(String searchItem) {
//		List<Note> resultNote = new ArrayList<>();
//		List<Note> getAllNote = nrepo.findAll();
//		for (Note noteData : getAllNote) {
//			if (noteData.getTitle().toLowerCase().contains(searchItem)
//					|| noteData.getDescription().toLowerCase().contains(searchItem)) {
//				System.out.println(noteData.getTitle() + "----" + noteData.getDescription());
//				resultNote.add(noteData);
//			}
//		}
//		return resultNote;
//	}

//	@Override
//	public boolean Note(String token, Long noteid) {
//		// TODO Auto-generated method stub
//		return false;
//	}

}
