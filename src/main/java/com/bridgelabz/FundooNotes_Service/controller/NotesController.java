package com.bridgelabz.FundooNotes_Service.controller;
import javax.validation.Valid;

import com.bridgelabz.FundooNotes_Service.dto.NotesDto;
import com.bridgelabz.FundooNotes_Service.dto.ResponseDTO;
import com.bridgelabz.FundooNotes_Service.model.Notes;
import com.bridgelabz.FundooNotes_Service.service.INotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notes")
public class NotesController {

	@Autowired
	INotesService notesService;

	/**
	 *
	 * @param notesDto
	 * @param token
	 * @purpose to create notes
	 */
	@PostMapping("/createnote/{token}")
	public ResponseEntity<ResponseDTO> createNote(@Valid@RequestBody NotesDto notesDto, @PathVariable String token) {
		Notes notesModel = notesService.createNote(notesDto,token);
		ResponseDTO response = new ResponseDTO("Note created successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}

	/**
	 *
	 * @param notesDto
	 * @param id
	 * @param token
	 * @purpose to update the notes
	 */
	@PutMapping("/updatenote/{id}/{token}")
	public ResponseEntity<ResponseDTO> updateNote(@Valid@RequestBody NotesDto notesDto,@PathVariable Long id,@PathVariable String token) {
		Notes notesModel = notesService.updateNote(notesDto,id,token);
		ResponseDTO response = new ResponseDTO("Note updated successfully",  notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param token
	 * @purpose to read the notes
	 */
	@GetMapping("/readnotes/{token}")
	public ResponseEntity<ResponseDTO> readAllNotes(@PathVariable String token){
		List<Notes> notesModel = notesService.readAllNotes(token);
		ResponseDTO response = new ResponseDTO("Fetching all notes successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param id
	 * @param token
	 * @purpose to read notes by id
	 */
	@GetMapping("/readnotesbyid/{id}/{token}")
	public ResponseEntity<ResponseDTO> readNotesById(@PathVariable Long id,@PathVariable String token){
		Optional<Notes> notesModel = notesService.readNotesById(id,token);
		ResponseDTO response = new ResponseDTO("Fetching notes by id successfully",notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param id
	 * @param token
	 * @return
	 */
	@PutMapping("/archeivenote/{id}/{token}")
	public ResponseEntity<ResponseDTO> archeiveNoteById(@PathVariable Long id,@PathVariable String token) {
		Notes notesModel = notesService.archieveNoteById(id,token);
		ResponseDTO response = new ResponseDTO("Archeived notes by id successfully",  notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param id
	 * @param token
	 * @purpose to unarchieve the notes
	 */
	@PutMapping("/unarcheivenote/{id}/{token}")
	public ResponseEntity<ResponseDTO> unArcheiveNoteById(@PathVariable Long id,@PathVariable String token) {
		Notes notesModel = notesService.unArchieveNoteById(id,token);
		ResponseDTO response = new ResponseDTO("Unarcheived notes by id successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param id
	 * @param token
	 * @purpose to put the notes to trash
	 */
	@PutMapping("/trashnote/{id}/{token}")
	public ResponseEntity<ResponseDTO> trashNote(@PathVariable Long id,@PathVariable String token) {
		Notes notesModel = notesService.trashNote(id,token);
		ResponseDTO response = new ResponseDTO("Notes added to trash successfully",notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param id
	 * @param token
	 * @purpose to restore notes
	 */
	@PutMapping("/restorenote/{id}/{token}")
	public ResponseEntity<ResponseDTO> restoreNote(@PathVariable Long id,@PathVariable String token) {
		Notes notesModel = notesService.restoreNote(id,token);
		ResponseDTO response = new ResponseDTO("Note restored successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param id
	 * @param token
	 * @purpose to delete notes
	 */
	@DeleteMapping("/deletenote/{id}/{token}")
	public ResponseEntity<ResponseDTO> deleteNote(@PathVariable Long id,@PathVariable String token) {
		Notes notesModel = notesService.deleteNote(id,token);
		ResponseDTO response = new ResponseDTO("Note deleted successfully",notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param id
	 * @param color
	 * @param token
	 * @purpose to change color of the notes
	 */
	@PutMapping("/changenotecolor/{id}/{token}")
	public ResponseEntity<ResponseDTO> changeNoteColor(@PathVariable Long id,@RequestParam String color,@PathVariable  String token) {
		Notes notesModel = notesService.changeNoteColor(id,color,token);
		ResponseDTO response = new ResponseDTO("Note color changed successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param id
	 * @param token
	 * @purpose to set pin notes
	 */
	@PutMapping("/pinnote/{id}/{token}")
	public ResponseEntity<ResponseDTO> pinNote(@PathVariable Long id,@PathVariable String token) {
		Notes notesModel = notesService.pinNote(id,token);
		ResponseDTO response = new ResponseDTO("Note pinned successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param id
	 * @param token
	 * @purpose to set unpin notes
	 */
	@PutMapping("/unpinnote/{id}/{token}")
	public ResponseEntity<ResponseDTO> unPinNote(@PathVariable Long id,@PathVariable  String token) {
		Notes notesModel = notesService.unPinNote(id,token);
		ResponseDTO response = new ResponseDTO("Note unpinned successfully",notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param token
	 * @purpose to get all pinned notes
	 */
	@GetMapping("/getallpinnednoted/{token}")
	public ResponseEntity<ResponseDTO> getAllPinnedNotes(@PathVariable String token) {
		List<Notes> notesModel = notesService.getAllPinnedNotes(token);
		ResponseDTO response = new ResponseDTO("Fetching all pinned notes successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param token
	 * @purose to get all archieve notes data
	 */
	@GetMapping("/getallarchievednotes/{token}")
	public ResponseEntity<ResponseDTO> getAllArchievedNotes(@PathVariable String token) {
		List<Notes> notesModel = notesService.getAllArchievedNotes(token);
		ResponseDTO response = new ResponseDTO("Fetching all archieved notes sucessfully",notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param token
	 * @purpose to get all trash data
	 */
	@GetMapping("/getalltrashtotes/{token}")
	public ResponseEntity<ResponseDTO> getAllTrashNotes(@PathVariable String token) {
		List<Notes> notesModel = notesService.getAllTrashNotes(token);
		ResponseDTO response = new ResponseDTO("Fetching all notes from trash successfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param notesId
	 * @param labelId
	 * @param token
	 * @purpose to display notes and label list
	 */
	@GetMapping("/notesLabelList")
	public ResponseEntity<ResponseDTO> notesLabelList(@RequestParam Long notesId,@RequestParam Long labelId,@RequestParam String token) {
		Notes notesModel = notesService.notesLabelList(notesId,labelId,token);
		ResponseDTO response = new ResponseDTO("notes and label mapping Successfull",  notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param notesId
	 * @param collaborator
	 * @param token
	 * @purpose to collaborate through email
	 */
	@PutMapping("/addcollaborator/{emailId}")
	public ResponseEntity<ResponseDTO> addCollaborator(@RequestParam Long notesId,@RequestParam String collaborator,@RequestHeader String token ) {
		Notes notesModel = notesService.addCollaborator(notesId,collaborator,token);
		ResponseDTO response = new ResponseDTO("collaborated sucessfully", notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 *
	 * @param remainderTime
	 * @param token
	 * @param notesId
	 * @purpose to set remainder
	 */
	@PutMapping("/setremaindertime/{notesId}")
	public ResponseEntity<ResponseDTO> setRemainderTime(@RequestParam String remainderTime,@RequestHeader String token,@PathVariable Long notesId) {
		Notes notesModel = notesService.setRemainderTime(remainderTime,token,notesId);
		ResponseDTO response = new ResponseDTO("Remainder time set sucessfully",  notesModel);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}