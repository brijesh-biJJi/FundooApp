package com.bridgelabz.fundoonotes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.UserDto;

@RestController
public class UserController {

	@PostMapping("/user/registration")
	public ResponseEntity<Response> register(@RequestBody UserDto info)
	{
	
	}
}
