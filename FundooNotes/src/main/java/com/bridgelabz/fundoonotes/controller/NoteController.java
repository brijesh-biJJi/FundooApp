package com.bridgelabz.fundoonotes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.response.Response;

@RestController
public class NoteController {

	/**
	 * API to create Notes
	 * @param noteDtoInfo
	 * @return
	 */
	@PostMapping("notes/create")
	public ResponseEntity<Response> createNote(@RequestBody NoteDto noteDtoInfo)
	{
		
		return ResponseEntity.status(HttpStatus.OK).body(new Response("Note is created successfully", 200, noteDtoInfo));
		
	}
}
