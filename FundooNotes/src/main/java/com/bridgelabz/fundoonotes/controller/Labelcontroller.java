package com.bridgelabz.fundoonotes.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.EditLabel;
import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.entity.LabelInformation;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.ILabelService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author Brijesh A Kanchan
 * @Purpose Controller class is to handle requests coming from the client. Then, the controller invokes a business class to process business-related tasks, and then redirects the client to a logical view name
 */
@RestController
@CrossOrigin("*")
public class Labelcontroller {
	
	@Autowired
	ILabelService labelService;
	
	/**
	 * API for creating Label
	 * @param token
	 * @param labelDtoInfo
	 * @return
	 */
	@PostMapping("label/createLable")
	@ApiOperation(value="Label Creation", response = Response.class)
	public ResponseEntity<Response> createLabel(@RequestHeader("token") String token,@RequestBody LabelDto labelDtoInfo)
	{
		LabelInformation labelInfo=labelService.createLabel(token,labelDtoInfo);
		if(labelInfo != null)
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Label is created successfully",  labelInfo));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Label already exists", labelInfo));
	
	}
	
	/**
	 * API for Add Label to Note if Label Exists Else Create Label and Add to Note
	 * @param token
	 * @param labelDtoInfo
	 * @param noteId
	 * @return
	 */
	@PutMapping("label/addMapLabel")
	@ApiOperation(value="Add label", response = Response.class)
	public ResponseEntity<Response> addMapLabel(@RequestHeader("token") String token,@RequestBody LabelDto labelDtoInfo,@RequestParam long noteId)
	{
		LabelInformation labelInfo=labelService.addMapLabel(token,labelDtoInfo,noteId);
		if(labelInfo != null)
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Label added successfully",  labelInfo));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Label not added", labelInfo));
	
	}
	
	/**
	 * API for removing Label from Note
	 * @param token
	 * @param labelDtoInfo
	 * @param noteId
	 * @return
	 */
	@PutMapping("label/removeNoteLabel")
	@ApiOperation(value="Remove Note Label", response = Response.class)
	public ResponseEntity<Response> removeNoteLabel(@RequestHeader("token") String token,@RequestBody LabelDto labelDtoInfo,@RequestParam long noteId)
	{
		LabelInformation labelInfo=labelService.removeNoteLabel(token,labelDtoInfo,noteId);
		if(labelInfo != null)
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Label successfully removed from Note",  labelInfo));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Label doesn't Exist", labelInfo));
	
	} 
	
	/**
	 * API for deleting the Label from User as well as from Note
	 * @param token
	 * @param labelDtoInfo
	 * @param noteId
	 * @return
	 */
	@DeleteMapping("label/deleteUserLabel")
	@ApiOperation(value="Delete Label Permanently", response = Response.class)
	public ResponseEntity<Response> deleteUserLabel(@RequestHeader("token") String token,@RequestBody LabelDto labelDtoInfo,@RequestParam long noteId)
	{
		LabelInformation labelInfo=labelService.deleteUserLabel(token,labelDtoInfo,noteId);
		if(labelInfo != null)
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Label deleted successfully...",  labelInfo));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Label doesn't Exist", labelInfo));
	
	}
	
	/**
	 * API for Editing the Label
	 * @param token
	 * @param labelDtoInfo
	 * @return
	 */
	@PutMapping("label/editLabel")
	@ApiOperation(value="Edit label", response = Response.class)
	public ResponseEntity<Response> editLabel(@RequestHeader("token") String token,@RequestBody EditLabel editlabelInfo)
	{
		LabelInformation labelInfo=labelService.editLabel(token,editlabelInfo);
		if(labelInfo!=null)
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Label Edited successfully...",  labelInfo));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Label not Edited", labelInfo));
	
	}
	
	/**
	 * API for Retrieving all Notes associated with particular Label
	 * @param token
	 * @param labelName
	 * @return
	 */
	@GetMapping("label/retrieveNotes")
	@ApiOperation(value="Get All Notes", response = Response.class)
	public ResponseEntity<Response> retrieveNotes(@RequestHeader("token") String token,@RequestParam String labelName)
	{
		List<NoteInformation> noteList=labelService.retrieveNotes(token,labelName);
		if(noteList!=null)
			return ResponseEntity.status(HttpStatus.OK).body(new Response("List of Notes Associated with "+labelName +" Label...",  noteList));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("No Notes Found", noteList));

	}
	
	/**
	 * API for retrieving List of Labels associated with respect to User
	 * @param token
	 * @return
	 */
	@GetMapping("label/getLabelsWrtUser")
	@ApiOperation(value="Get All Labels", response = Response.class)
	public ResponseEntity<Response> getLabelsWrtUser(@RequestHeader("token") String token)
	{
		List<LabelInformation> labelList=labelService.getLabelsWrtUser(token);
		if(labelList !=null)
			return ResponseEntity.status(HttpStatus.OK).body(new Response("List of Labels Associated with the User...",  labelList));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Label not Found", labelList));
	
	}
}
