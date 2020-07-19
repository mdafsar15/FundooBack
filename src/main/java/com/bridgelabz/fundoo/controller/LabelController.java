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

import com.bridgelabz.fundoo.dto.AddLabel;
import com.bridgelabz.fundoo.dto.EmailDto;
import com.bridgelabz.fundoo.dto.LabelDto;
import com.bridgelabz.fundoo.dto.LabelUpdate;
import com.bridgelabz.fundoo.model.Label;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.service.ILabelService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/label")
@CrossOrigin(origins = "http://localhost:4200")

public class LabelController {

	@Autowired
	private ILabelService lService;

	@PostMapping("/create")
	public ResponseEntity<Response> createLabel(@RequestBody LabelDto labelDto, @RequestHeader("token") String token) {
		if (lService.createLabel(labelDto, token)) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("label is Created",200));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("label is not Created",400));
	}

	@PutMapping("/updateLabel")
	public ResponseEntity<Response> updateLabel(@RequestBody LabelUpdate lableUpdate,
			@RequestHeader("token") String token) {
		if (lService.updateLabel(lableUpdate, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("label is Updated",200));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new Response("label is not  Updated",400));
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Response> deleteLabel(@RequestParam("l_Id") Long l_Id,
			@RequestHeader("token") String token, @RequestParam("n_id") Long n_id) {
		lService.removeLabel(l_Id, n_id, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("label is deleted",200));

	}

	@PostMapping("/addlabels/{n_id}")
	public ResponseEntity<Response> addlabel(@RequestBody LabelDto labelDto,@RequestHeader("token") String token, 
			@PathVariable("n_id") Long n_id) {
		lService.addLabel(labelDto,n_id, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("label is added",200));

	}

	@GetMapping("/fetch")
	@ApiOperation(value = "To get all the labels from user")
	public ResponseEntity<Response> getAllLabels(@RequestHeader("token") String token) {
		List<Label> foundLabelList = lService.foundLabelsList(token);
		if (!foundLabelList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("found labels", 200, foundLabelList));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Opps...No labels founds", 400));
	}

	@PutMapping("/edit")
	@ApiOperation(value = "To edit name of label")
	public ResponseEntity<Response> editLabelName(@RequestHeader("token") String token, @RequestBody LabelDto labelDTO,
			@RequestParam("labelId") long labelId) {
		if (lService.editLabel(token, labelDTO, labelId)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("label name changed", 200));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("New label cannot be the same", 400));
	}

//	@PostMapping("/create")
//	@ApiOperation(value = "To create a label")
//	public ResponseEntity<Response> createLabel(@RequestHeader("token") String token, @RequestBody LabelDto labelDto) {
//		long labelId = lService.createLabel(token, labelDto);
//		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Label created!", 200, labelId));
//	}
//
//	@PostMapping("/create/{noteId}")
//	@ApiOperation(value = "To create label and it's note")
//	public ResponseEntity<Response> createandMapLabel(@RequestHeader("token") String token,
//			@RequestBody LabelDto labelDTO, @PathVariable("noteId") long noteId) {
//		lService.createLabelAndMap(token, noteId, labelDTO);
//		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("label created and mapped", 200, labelDTO));
//	}
//

//
//	@DeleteMapping("/{labelId}/delete")
//	@ApiOperation(value = "To delete a label")
//	public ResponseEntity<Response> deleteLabel(@RequestHeader("token") String token,
//			@PathVariable("labelId") long labelId) {
//		if (lService.deleteLabel(token, labelId)) {
//			return ResponseEntity.status(HttpStatus.OK).body(new Response("label deleted sucessfully", 200));
//		}
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Cannot delete label", 400));
//	}
//

//	@PostMapping("/addlabels")
//	@ApiOperation(value = "To add a note to existing label")
//	public ResponseEntity<Response> addLabelsToNote(@RequestHeader("token") String token, @RequestBody AddLabel addLabel) {
//		lService.addNoteLabel(token, addLabel.getLabelId(),addLabel.getNoteId());
//		return ResponseEntity.status(HttpStatus.OK).body(new Response("note added to the label", 200));
//	}

//	@PostMapping("/addlabels")
//	@ApiOperation(value = "To add a note to existing label")
//	public ResponseEntity<Response> addLabelsToNote(@RequestHeader("token") String token,
//			@RequestParam(value = "noteId", required = false) Long noteId, @RequestParam("labelId") long labelId) {
//		lService.addNoteLabel(token, noteId, labelId);
//		return ResponseEntity.status(HttpStatus.OK).body(new Response("note added to the label", 200));
//	}

//	@PostMapping("/addlabels")
//	@ApiOperation(value = "To add a note to existing label")
//	public ResponseEntity<Response> addLabelsToNote(@RequestHeader("token") String token,
//			@RequestParam(value="noteId", required = false) Long noteId, @RequestParam("labelId") long labelId) {
//		lService.addNoteLabel(token, noteId, labelId);
//		return ResponseEntity.status(HttpStatus.OK).body(new Response("note added to the label", 200));
//	}

//	 @PostMapping("/addlabels")
//	 public ResponseEntity<Response> addLabelsToNote(@RequestParam("labelId") Long labelId,@RequestHeader("token") String token,@RequestParam ("notesId") Long notesId){
//		  lService.addNoteLabel(token, notesId, labelId);
//		 return ResponseEntity.status(HttpStatus.OK).body(new Response("label is added", 200));
//		 
//	 }

//	@PatchMapping("/remove")
//	@ApiOperation(value = "To remove a note from a label")
//	public ResponseEntity<Response> removeLabelsfromNote(@RequestHeader("token") String token,
//			@RequestParam("noteId") long noteId, @RequestParam("labelId") long labelId) {
//		lService.remoNoteLabel(token, noteId, labelId);
//		return ResponseEntity.status(HttpStatus.OK).body(new Response("note removed from the label", 200));
//	}
}