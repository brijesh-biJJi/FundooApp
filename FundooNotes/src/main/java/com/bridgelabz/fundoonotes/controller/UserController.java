package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.LoginDto;
import com.bridgelabz.fundoonotes.dto.RegisterDto;
import com.bridgelabz.fundoonotes.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.IUserServices;
import com.bridgelabz.fundoonotes.utility.JWTGenerator;



/**
 * 
 * @author Brijesh A Kanchan
 *
 */
@RestController
public class UserController {

	@Autowired
	private IUserServices userService;
	
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
		boolean result = userService.register(registerDtoInfo);
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
		UserInformation userInfo = userService.login(loginDtoInfo);
		if(userInfo != null) 
		{
			String token=jwtGenerator.createToken(userInfo.getUserid());
			return ResponseEntity.status(HttpStatus.ACCEPTED).header("Login Successfully",userInfo.getEmail()).body(new Response(token, 200, loginDtoInfo));
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
		boolean updateUserInfo=userService.updateIsVerify(token);
		if(updateUserInfo)
		{
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response(token, 200, "VERIFIED"));
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response(token, 401, "Not Verified. Unauthorized Client...!"));
	}
	
	/**
	 * API for forgot password
	 * @param email
	 * @return
	 */
	@PostMapping("user/forgotpass")
	public ResponseEntity<Response> forgotPassword(@RequestParam("email") String email)
	{
		boolean res = userService.isUserExist(email);
		if (res) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("User Exist", 200, email));
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("User does not Exist", 400, email));

	}
	
	/**
	 * API for Updating password of specified user
	 * @param token
	 * @param update
	 * @return
	 */
	@PutMapping("user/update/{token}")
	public ResponseEntity<Response> update(@PathVariable("token") String token, @RequestBody UpdatePassword passwordUpdateInfo) {
		boolean res = userService.updatePassword(passwordUpdateInfo, token);
		if (res) {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new Response("Password Updated Successfully...!", 200, passwordUpdateInfo));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Entered Password  doesn't Match...!", 402, passwordUpdateInfo));
	}
	
	/**
	 * API for getting all user details
	 * @return
	 */
	@GetMapping("user/getusers")
	public ResponseEntity<Response> getUsers() {
		List<UserInformation> userList = userService.getUsers();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("List of all Users.", 200, userList));
	}
}
