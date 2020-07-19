package com.bridgelabz.fundoo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.model.Note;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.service.ICollaboratorService;

@RestController
@RequestMapping("/collaborators")
public class CollaboratorController {
	@Autowired
	private ICollaboratorService 	serviceCollaborator;

//	@Autowired
//	private ICollaboratorService cService;
//
//	@PostMapping("add/{noteId}")
//	public ResponseEntity<Response> addCollaborator(@RequestHeader("token") String token,
//			@PathVariable("noteId") long noteId, @RequestParam("emailId") String email) {
//		if (cService.addCollaborator(token, noteId, email)) {
//			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("Collaborator added!", 202, email));
//		}
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Error adding collaborator", 400));
//
//	}

	@PostMapping("/add")
	public ResponseEntity<Response> addsCollaborator(@RequestParam("noteId") Long noteId,
			@RequestParam("email") String email, @RequestHeader("token") String token){
		Note note=serviceCollaborator.addCollaborator(noteId, email, token);
		 if(note!=null) {
			 return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Collaborator is Added",200,noteId));
		 }
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Response("Collaborator is not added", 202));
		
	}
}