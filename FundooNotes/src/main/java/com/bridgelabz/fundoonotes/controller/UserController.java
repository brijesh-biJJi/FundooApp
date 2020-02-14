package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.LoginDto;
import com.bridgelabz.fundoonotes.dto.RegisterDto;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.Services;
import com.bridgelabz.fundoonotes.utility.JWTGenerator;



/**
 * 
 * @author Brijesh A Kanchan
 *
 */
@RestController
public class UserController {

	@Autowired
	private Services service;
	
	@Autowired
	private JWTGenerator jwtGenerator;
	
	/**
	 * API for Registration
	 * register is a Handler method that communicates with Service layer in order to perform Registration Operation
	 * @param info
	 * @return
	 */
	@PostMapping("/user/register")
	public ResponseEntity<Response> register(@RequestBody RegisterDto registerDtoInfo)
	{
		boolean result = service.register(registerDtoInfo);
		if(result) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Registration Successfull...!", 200, registerDtoInfo));
		}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new Response("User Already Exixts...!", 208, registerDtoInfo));
		

		//return ResponseEntity.status(HttpStatus.OK).body(new Response("Hello ", 200, info));
	}
	
	/**
	 * API for Login
	 * login is a Handler method that communicates with Service layer in order to perform Login Operation
	 * @param info
	 * @return
	 */
	@PostMapping("/user/login")
	public ResponseEntity<Response> login(@RequestBody LoginDto loginDtoInfo)
	{
		UserInformation userInfo = service.login(loginDtoInfo);
		if(userInfo != null) 
		{
			String token=jwtGenerator.createToken(userInfo.getUserid());
			return ResponseEntity.status(HttpStatus.ACCEPTED).header("Login Successfully",userInfo.getEmail()).body(new Response("Login Successfully", 200, loginDtoInfo));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Invalid User...!", 400, loginDtoInfo));
	}
	
	/**
	 * API for Token Verification
	 * @param token
	 * @return
	 */
	@GetMapping("/verify/{token}")
	public ResponseEntity<Response> userVerification(@PathVariable ("token") String token)
	{
		boolean updateUserInfo=service.updateIsVerify(token);
		if(updateUserInfo)
		{
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response(token, 200, "VERIFIED"));
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response(token, 401, "Not Verified. Unauthorized Client...!"));
	}
}
