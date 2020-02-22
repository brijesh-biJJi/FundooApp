package com.bridgelabz.fundoonotes.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.EditLabel;
import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.entity.LabelInformation;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.ILabelService;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
@RestController
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
	public ResponseEntity<Response> createLabel(@RequestHeader("token") String token,@RequestBody LabelDto labelDtoInfo)
	{
		LabelInformation labelInfo=labelService.createLabel(token,labelDtoInfo);
		if(labelInfo != null)
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Label is created successfully", 200, labelInfo));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Label already exists", 400,labelInfo));
	
	}
	
	/**
	 * API for Add Label to Note if Label Exists Else Create Label and Add to Note
	 * @param token
	 * @param labelDtoInfo
	 * @param noteId
	 * @return
	 */
	@PutMapping("label/addMapLabel")
	public ResponseEntity<Response> addMapLabel(@RequestHeader("token") String token,@RequestBody LabelDto labelDtoInfo,@RequestParam long noteId)
	{
		LabelInformation labelInfo=labelService.addMapLabel(token,labelDtoInfo,noteId);
		if(labelInfo != null)
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Label added successfully", 200, labelInfo));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Label not added", 400,labelInfo));
	
	}
	
	/**
	 * API for removing Label from Note
	 * @param token
	 * @param labelDtoInfo
	 * @param noteId
	 * @return
	 */
	@PutMapping("label/removeNoteLabel")
	public ResponseEntity<Response> removeNoteLabel(@RequestHeader("token") String token,@RequestBody LabelDto labelDtoInfo,@RequestParam long noteId)
	{
		LabelInformation labelInfo=labelService.removeNoteLabel(token,labelDtoInfo,noteId);
		if(labelInfo != null)
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Label successfully removed from Note", 200, labelInfo));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Label doesn't Exist", 400,labelInfo));
	
	} 
	
	/**
	 * API for deleting the Label from User as well as from Note
	 * @param token
	 * @param labelDtoInfo
	 * @param noteId
	 * @return
	 */
	@DeleteMapping("label/deleteUserLabel")
	public ResponseEntity<Response> deleteUserLabel(@RequestHeader("token") String token,@RequestBody LabelDto labelDtoInfo,@RequestParam long noteId)
	{
		LabelInformation labelInfo=labelService.deleteUserLabel(token,labelDtoInfo,noteId);
		if(labelInfo != null)
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Label deleted successfully...", 200, labelInfo));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Label doesn't Exist", 400,labelInfo));
	
	}
	
	/**
	 * API for Editing the Label
	 * @param token
	 * @param labelDtoInfo
	 * @return
	 */
	@PutMapping("label/editLabel")
	public ResponseEntity<Response> editLabel(@RequestHeader("token") String token,@RequestBody EditLabel editlabelInfo)
	{
		LabelInformation labelInfo=labelService.editLabel(token,editlabelInfo);
		if(labelInfo!=null)
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Label Edited successfully...", 200, labelInfo));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Label not Edited", 400,labelInfo));
	
	}
}
