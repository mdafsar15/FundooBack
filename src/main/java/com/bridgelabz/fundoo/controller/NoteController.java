package com.bridgelabz.fundoo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.dto.NoteDto;
import com.bridgelabz.fundoo.dto.ReminderDto;
import com.bridgelabz.fundoo.dto.UpdateNoteDto;
import com.bridgelabz.fundoo.model.Note;
import com.bridgelabz.fundoo.response.NoteResponse;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.service.ICollaboratorService;
import com.bridgelabz.fundoo.service.INoteService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("note")
@CrossOrigin(origins = "http://localhost:4200")

public class NoteController {

	@Autowired
	private INoteService nService;
	
	@Autowired
	private ICollaboratorService 	serviceCollaborator;

	@ApiOperation(value = "To create a new note for a user")

	@PostMapping("create")
	public ResponseEntity<Response> createNote(@RequestBody NoteDto nDto, @RequestHeader("token") String token) {
		System.out.println("Inside create note controller");
		if (nService.createNote(nDto, token)) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Note created!", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Error creating note", 400));
	}

	@ApiOperation(value = "To delete an existing note")

	@DeleteMapping("{id}/delete")
	public ResponseEntity<Response> deleteNote(@PathVariable("id") long noteId, @RequestHeader("token") String token) {
		if (nService.deleteNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Note deleted! ", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Error deleting note ", 400));

	}

	@ApiOperation(value = "To update an existing note")

	@PutMapping("update")
	public ResponseEntity<Response> updateNote(@RequestBody UpdateNoteDto noteDto,
			@RequestHeader("token") String token) {
		if (nService.updateNote(noteDto, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Note updated! ", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Error updating note  ", 400));
	}

	@ApiOperation(value = "To fetch all notes of a user")

	@GetMapping("fetch/notes")
	public ResponseEntity<NoteResponse> fetchNotes(@RequestHeader("token") String token) {
		List<Note> notes = nService.getallNotes(token);
		System.out.println("notes for response : " + notes);
		if (!notes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new NoteResponse("Notes are", 200, notes));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new NoteResponse("Error fetching notes", 400, null));
	}

	@ApiOperation(value = "To archive a note of a user")

	@DeleteMapping("{id}/archive")
	public ResponseEntity<Response> archiveNote(@PathVariable("id") long noteId, @RequestHeader("token") String token) {
		if (nService.archiveNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("note archived", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Already archived", 400));
	}

	@ApiOperation(value = "To pin/unpin a note of a user")
	@PatchMapping("{id}/pin")
	public ResponseEntity<Response> pinNote(@PathVariable("id") long noteId, @RequestHeader("token") String token) {
		if (nService.pinNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("note pinned", 200));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("note unpinned", 400));
	}

	@ApiOperation(value = "To change the color of a note of a user")

	@PatchMapping("{id}")
	public ResponseEntity<Response> changeColour(@RequestHeader("token") String token, @PathVariable("id") long noteId,
			@RequestParam("color") String noteColour) {
		nService.changeColour(token, noteId, noteColour);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("color changed", 200));
	}

	@ApiOperation(value = "To trash the note of a user")

	@DeleteMapping("{id}/trash")
	public ResponseEntity<Response> trashNote(@PathVariable("id") long noteId, @RequestHeader("token") String token) {
		if (nService.trashNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("note trashed", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Already trashed!", 400));
	}

//	@ApiOperation(value = "To set a reminder for a note by a user")
//	@PutMapping("{id}/reminder/add")
//	public ResponseEntity<Response> setReminder(@RequestHeader("token") String token, @PathVariable("id") long noteId,
//			@RequestBody ReminderDto reminderDTO) {
//		nService.addReminder(token, noteId, reminderDTO);
//		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Reminder created", 200));
//	}
//
//	@ApiOperation(value = "To delete reminder of a note by a user")
//
//	@DeleteMapping("{id}/reminder/delete")
//	public ResponseEntity<Response> removeReminder(@RequestHeader("token") String token,
//			@PathVariable("id") long noteId) {
//		nService.deleteReminder(token, noteId);
//		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Reminder removed!", 200));
//	}
	
	@PostMapping("/addReminder/")
	public ResponseEntity<Response> reminder(@RequestParam("noteId") Long noteId, @RequestHeader String token ,@RequestBody ReminderDto reminder){
      if(nService.addReminder(token, noteId, reminder)){
    	return  ResponseEntity.status(HttpStatus.CREATED).body(new Response("Reminder is Added",200,noteId));
      }
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("unable to add Reminder",400));
	}
	

	@PostMapping("/removeReminder/")
	public ResponseEntity<Response> removeReminder(@RequestParam("noteId") Long noteId, @RequestHeader String token){
      if(nService.removeReminder(token, noteId)){
    	return  ResponseEntity.status(HttpStatus.CREATED).body(new Response("Reminder is removed",200));
      }
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("unable to remove remainder",400,noteId));
	}

	@ApiOperation(value = "To restore a deleted note by the user")
	@PutMapping("{id}/restore")
	public ResponseEntity<Response> restoreNote(@PathVariable("id") long noteId, @RequestHeader("token") String token) {
		if (nService.restoreNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Note restored", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Opps...Error Restoring note!", 400));

	}

	@ApiOperation(value = "To fetch all trashed notes for user")

	@GetMapping("fetch/notes/trashed")
	public ResponseEntity<Response> fetchTrashedNotes(@RequestHeader("token") String token) {
		List<Note> trashedNotes = nService.getTrashed(token);
		if (!trashedNotes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Trashed notes are", 200, trashedNotes));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Empty trash", 400));
	}

	@ApiOperation(value = "To fetch all archived notes for user")

	@GetMapping("fetch/notes/archived")
	public ResponseEntity<Response> fetchArchivedNotes(@RequestHeader("token") String token) {
		List<Note> archiveNotes = nService.getArchived(token);
		if (!archiveNotes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Trashed notes are", 200, archiveNotes));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Empty trash", 400));
	}

	@ApiOperation(value = "To fetch all notes that have reminder set from user")

	@GetMapping("fetch/notes/reminders")
	public ResponseEntity<Response> fetchAllReminderNotes(@RequestHeader("token") String token) {
		List<Note> reminderNotes = nService.getAllReminderNotes(token);
		if (!reminderNotes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Trashed notes are", 200, reminderNotes));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Empty trash", 400));
	}

	@ApiOperation(value = "To get all pinned notes for user")

	@GetMapping("fetch/notes/pinned")
	public ResponseEntity<Response> fetchPinnedNotes(@RequestHeader("token") String token) {
		List<Note> pinnedNotes = nService.getPinned(token);
		if (!pinnedNotes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Pinned notes are", 200, pinnedNotes));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("No notes pinned", 400));
	}

	@GetMapping("fetch/note/{name}")
	public ResponseEntity<Response> findNote(@RequestHeader("token") String token, String name) {
		long  noteId = nService.findId(token,name);
		System.out.println(noteId);
			return ResponseEntity.status(HttpStatus.OK).body(new Response("noteId", 200, noteId));
		}
	

	

//	@DeleteMapping("/collaborator/removeCollaborator")
//	public ResponseEntity<Response> removeCollaborator(@RequestParam("notesId") Long notesId,
//			@RequestParam("email") String email, @RequestHeader("token") String token){
//		      serviceCollaborator.removeCollaborator(notesId, email, token);
//			 return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Collaborator is deleted"));
//	
//			
//	}
//	
//	@GetMapping("/collaborator/getAllCollaborators")
//	public ResponseEntity<Response> getAllCollaborators(@RequestHeader("token") String token){
//		List<NotesEntity> collabNotes=serviceCollaborator.getAllNotesCollaborators(token);
//		if(collabNotes!=null) {
//			return  ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("list of your Collaborated  notes", collabNotes));
//		}
//		return  ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("No collaborator notes", collabNotes));
//		
//	}
}