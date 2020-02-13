package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.Services;

@RestController
public class UserController {

	@Autowired
	private Services service;
	@PostMapping("/user/register")
	public ResponseEntity<Response> register(@RequestBody UserDto info)
	{
		boolean result = service.register(info);
		if(result) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("registration successfull", 200, info));
		}
				return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new Response("user already ", 400, info));
		
		
		
		//return ResponseEntity.status(HttpStatus.OK).body(new Response("Hello ", 400, info));
	
	}
}
