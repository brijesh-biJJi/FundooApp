package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.UpdateNotes;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.INoteService;

@RestController
public class NoteController {
	@Autowired
	private INoteService noteService;
	/**
	 * API to create Notes
	 * @param noteDtoInfo
	 * @return
	 */
	@PostMapping("notes/create")
	public ResponseEntity<Response> createNote(@RequestBody NoteDto noteDtoInfo,@RequestHeader("token") String token)
	{
		NoteInformation noteInfo=noteService.createNote(noteDtoInfo, token);
		if(noteInfo != null)
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Note is created successfully", 200, noteDtoInfo));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Something went wrong", 400,noteInfo));
	}
	
	/**
	 * API for updating a Note
	 * @param note
	 * @param token
	 * @return
	 */
	@PutMapping("/note/update")
	public ResponseEntity<Response> updateNote(@RequestBody UpdateNotes updateNoteinfo, @RequestHeader("token") String token) {
		noteService.updateNote(updateNoteinfo, token);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Note Updated successfully..!", 200, updateNoteinfo));
	}
	
	/**
	 * API for pin a Note
	 * @param id
	 * @param token
	 * @return
	 */
	@PutMapping("/note/pin/{id}")
	public ResponseEntity<Response> pinNote(@PathVariable Long id, @RequestHeader("token") String token) {
		boolean res=noteService.pinNote(id, token);
		return (res) ? ResponseEntity.status(HttpStatus.OK).body(new Response("Note is pinned successfully....!", 200, token))
				 : ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new Response("Note doesn't Pinned...!", 400, token));
	}
	
	/**
	 * API for archive a Note
	 * @param id
	 * @param token
	 * @return
	 */
	@PutMapping("note/archive/{id}")
	public ResponseEntity<Response> archiveNote(@PathVariable Long id,@RequestHeader("token") String token )
	{
		boolean res=noteService.archiveNote(id, token);
		return (res) ? ResponseEntity.status(HttpStatus.OK).body(new Response("Note is Archived Successfully....!", 200, token))
					 : ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new Response("Note doesn't Archived...!", 400, token));
	}
	
	
	/**
	 * API for moving Note into Trash
	 * @param id
	 * @param token
	 * @return
	 */
	@DeleteMapping("note/delete/{id}")
	public ResponseEntity<Response> deleteNote(@PathVariable Long id,@RequestHeader("token") String token) throws Exception
	{
		boolean res=noteService.moveToTrash(id,token);
		return (res) ? ResponseEntity.status(HttpStatus.OK).body(new Response("Note successfull moved to the Trash ..!!", 200,token))
					 : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Note ID is not Available..!!", 400,token));	
	}
	
	/**
	 * API for Deleting the Note Permanently
	 * @param id
	 * @param token
	 * @return
	 */
	 @DeleteMapping("note/deletepermanently/{id}")
	 public ResponseEntity<Response> deleteNotePermanently(@PathVariable Long id,@RequestHeader("token") String token)
	 {
		 boolean res=noteService.deleteNotePermanently(id,token);
		 return (res) ? ResponseEntity.status(HttpStatus.OK).body(new Response("Note is deleted permanently", 200,token))
					  : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("The note is not available", 400,token));
	 }
}

