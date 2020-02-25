package com.bridgelabz.fundoonotes.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.ReminderDto;
import com.bridgelabz.fundoonotes.dto.UpdateNotes;
import com.bridgelabz.fundoonotes.entity.LabelInformation;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.INoteService;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
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
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Note is created successfully",  noteDtoInfo));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Something went wrong", noteInfo));
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
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Note Updated successfully..!", updateNoteinfo));
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
		return (res) ? ResponseEntity.status(HttpStatus.OK).body(new Response("Note is pinned successfully....!", token))
				 : ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new Response("Note doesn't Pinned...!",  token));
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
		return (res) ? ResponseEntity.status(HttpStatus.OK).body(new Response("Note is Archived Successfully....!",  token))
					 : ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new Response("Note doesn't Archived...!",  token));
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
		return (res) ? ResponseEntity.status(HttpStatus.OK).body(new Response("Note successfull moved to the Trash ..!!", token))
					 : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Note ID is not Available..!!", token));	
	}
	
	/**
	 * API for Deleting the Note Permanently
	 * @param id
	 * @param token
	 * @return
	 */
	 @DeleteMapping("note/deletePermanently/{id}")
	 public ResponseEntity<Response> deleteNotePermanently(@PathVariable Long id,@RequestHeader("token") String token)
	 {
		 boolean res=noteService.deleteNotePermanently(id,token);
		 return (res) ? ResponseEntity.status(HttpStatus.OK).body(new Response("Note is deleted permanently",token))
					  : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("The note is not available", token));
	 }
	 
	 /**
	  * API for Changing the Note Color
	  * @param noteId
	  * @param color
	  * @param token
	  * @return
	  */
	 @PutMapping("note/changecolor")
	 public ResponseEntity<Response> changeColor(@RequestParam("noteId") Long noteId,@RequestParam("color") String color,@RequestHeader("token") String token)
	 {
		boolean res=noteService.changeColor(noteId,color,token);
		return (res) ? ResponseEntity.status(HttpStatus.OK).body(new Response("Color is added successfully",token))
					 : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Color is not changed", token));
	 }
	 
	 /**
	  * API for retrieving all the Notes
	  * @param token
	  * @return
	  */
	 @GetMapping("/note/getAllNotes")
	 public ResponseEntity<Response> getAllNotes(@RequestHeader("token") String token)
	 {

		List<NoteInformation> notesList = noteService.getAllNotes(token);
		if(notesList != null)
			return ResponseEntity.status(HttpStatus.OK).body(new Response("The notes are",  notesList));
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("No Notes Found..!",  notesList));
	 }
	 
	 /**
	  * API for retrieving all Trashed Notes
	  * @param token
	  * @return
	  */
	 @GetMapping("note/getTrashedNotes")
	 public ResponseEntity<Response> getTrashedNote(@RequestHeader("token") String token)
	 {
		 List<NoteInformation> notesList = noteService.getTrashedNotes(token);
		 if(notesList != null)
				return ResponseEntity.status(HttpStatus.OK).body(new Response("Trashed notes are",  notesList));
			else
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("No Notes Found..!",  notesList)); 
	 }
	 
	 /**
	  * API for retrieving all the Archived Notes
	  * @param token
	  * @return
	  */
	 @GetMapping("note/getArchivedNotes")
	 public ResponseEntity<Response> getArchivedNote(@RequestHeader("token") String token)
	 {
		 List<NoteInformation> noteList=noteService.getArchivedNotes(token);
		 if(noteList !=null)
			 return ResponseEntity.status(HttpStatus.OK).body(new Response("Archived notes are", noteList));
			else
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("No Notes Found..!",  noteList)); 
	  
	 }
	 
	 /**
	  * API for retrieving all the Pinned Notes
	  * @param token
	  * @return
	  */
	 @GetMapping("note/getPinnedNotes")
	 public ResponseEntity<Response> getPinnedNote(@RequestHeader("token") String token)
	 {
		 List<NoteInformation> noteList=noteService.getPinnedNotes(token);
		 if(noteList !=null)
			 return ResponseEntity.status(HttpStatus.OK).body(new Response("Pinned notes are",  noteList));
			else
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("No Notes Found..!", noteList)); 
	  
	 }
	 
	 /**
	  * API for adding Reminder for Note 
	  * @param token
	  * @param noteId
	  * @param reminderDtoInfo
	  * @return
	  */
	 @PutMapping("note/addReminder")
	 public ResponseEntity<Response> addReminder(@RequestHeader("token") String token,@RequestParam("noteId") long noteId,@RequestBody ReminderDto reminderDtoInfo)
	 {
		 boolean res=noteService.addReminder(token,noteId,reminderDtoInfo);
			return (res) ? ResponseEntity.status(HttpStatus.OK).body(new Response("Reminder added successfully", token))
						 : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Reminder not added", token));
	 }
	 
	 /**
	  * API for removing Reminder for Note 
	  * @param token
	  * @param noteId
	  * @param reminderDtoInfo
	  * @return
	  */
	 @PutMapping("note/removeReminder")
	 public ResponseEntity<Response> removeReminder(@RequestHeader("token") String token, @RequestParam("noteId") long noteId,@RequestBody ReminderDto reminderDtoInfo)
	 {
		boolean res=noteService.removeReminder(token,noteId,reminderDtoInfo);
		return (res) ? ResponseEntity.status(HttpStatus.OK).body(new Response("Reminder removed successfully", token))
					 : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Reminder not removed", token));
	 }
	 
	 /**
	  * API for Retrieving All the Labels associated with particular Note
	  * @param token
	  * @param noteId
	  * @return
	  */
	 @GetMapping("note/retrieveLabels")
	 public ResponseEntity<Response> retrieveLabels(@RequestHeader("token") String token,@RequestParam long noteId)
	{
		List<LabelInformation> labelList=noteService.retrieveLabels(token,noteId);
		if(labelList!=null)
			return ResponseEntity.status(HttpStatus.OK).body(new Response("List of Labels Associated with the Note...", labelList));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Labels not Found", labelList));
		
	}
}

