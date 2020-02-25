package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.CollaboratorDto;
import com.bridgelabz.fundoonotes.entity.CollaboratorInformation;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.ICollaboratorService;

@RestController
public class CollaboratorController {
	
	@Autowired
	ICollaboratorService collabService;
	
	@PostMapping("collab/createCollab")
	public ResponseEntity<Response> createCollab(@RequestHeader("token") String token,@RequestBody CollaboratorDto collabDtoInfo,@RequestParam("noteid") long noteId){
		CollaboratorInformation collabInfo=collabService.createCollab(token,collabDtoInfo,noteId);
		if(collabInfo != null)
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Collaborator is created successfully",  collabInfo));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Collaborator already exists",collabInfo));
	
	}
	

}
