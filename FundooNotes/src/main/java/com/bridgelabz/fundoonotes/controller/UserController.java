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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.LoginDto;
import com.bridgelabz.fundoonotes.dto.RegisterDto;
import com.bridgelabz.fundoonotes.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.User;
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
	@PostMapping("/users/register")
	public ResponseEntity<Response> register(@RequestBody RegisterDto registerDtoInfo)
	{
		User userInfo = userService.register(registerDtoInfo);
		if(userInfo != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Registration Successfull...!",  registerDtoInfo));
		}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new Response("User Already Exists...!",  registerDtoInfo));
		

		//return ResponseEntity.status(HttpStatus.OK).body(new Response("Hello ", 200, info));
	}
	
	/**
	 * API for Login
	 * login is a Handler method that communicates with Service layer in order to perform Login Operation
	 * @param info
	 * @return
	 */
	@PostMapping("/users/login")
	public ResponseEntity<Response> login(@RequestBody LoginDto loginDtoInfo)
	{
		User userInfo = userService.login(loginDtoInfo);
		if(userInfo != null) 
		{
			String token=jwtGenerator.createToken(userInfo.getUserid());
			return ResponseEntity.status(HttpStatus.ACCEPTED).header("Login Successfully",userInfo.getEmail()).body(new Response(token,  loginDtoInfo));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Invalid User...!",  loginDtoInfo));
	}
	
	/**
	 * API for Token Verification
	 * @param token
	 * @return
	 */
	@GetMapping("users/verify/{token}")
	public ResponseEntity<Response> userVerification(@PathVariable ("token") String token)
	{
		boolean updateUserInfo=userService.updateIsVerify(token);
		if(updateUserInfo)
		{
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("VERIFIED",token));
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("Not Verified. Unauthorized Client...!",updateUserInfo));
	}
	
	/**
	 * API for forgot password
	 * @param email
	 * @return
	 */
	@PostMapping("users/forgotpassword")
	public ResponseEntity<Response> forgotPassword(@RequestParam("email") String email)
	{
		boolean res = userService.isUserExist(email);
		if (res) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("User Exist", email));
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("User does not Exist", email));

	}
	
	/**
	 * API for Updating password of specified user
	 * @param token
	 * @param update
	 * @return
	 */
	@PutMapping("users/updatePassword/{token}")
	public ResponseEntity<Response> update(@PathVariable("token") String token, @RequestBody UpdatePassword passwordUpdateInfo) {
		boolean res = userService.updatePassword(passwordUpdateInfo, token);
		if (res) {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new Response("Password Updated Successfully...!",  passwordUpdateInfo));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Entered Password  doesn't Match...!",  passwordUpdateInfo));
	}
	
	/**
	 * API for getting all user details
	 * @return
	 */
	@GetMapping("users")
	public ResponseEntity<Response> getUsers() {
		List<User> userList = userService.getUsers();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("List of all Users.", userList));
	}
	
	/**
	 * API for adding collaborator
	 * @param token
	 * @param collabDtoInfo
	 * @param noteId
	 * @return
	 */
	@PostMapping("collaborator/add")
	public ResponseEntity<Response> addCollab(@RequestHeader("token") String token,@RequestParam("email") String email,@RequestParam("noteid") long noteId){
		NoteInformation noteInfo=userService.addCollab(token,email,noteId);
		if(noteInfo != null)
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Collaborator is created successfully",  noteInfo));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Collaborator already exists",noteInfo));
	
	}
	
	/**
	 * API for retrieving Collaborator List
	 * @param token
	 * @param noteId
	 * @return
	 */
	@GetMapping("collaborators")
	public ResponseEntity<Response> getCollab(@RequestHeader("token") String token,@RequestParam("noteid") long noteId){
		List<User> collabList=userService.getCollab(token,noteId);
		if(collabList != null)
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Collaborator is created successfully",  collabList));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Collaborator already exists",collabList));
	
	}
	
	/**
	 * API for removing specified Collaborator
	 * @param token
	 * @param email
	 * @param noteId
	 * @return
	 */
	@PutMapping("collaborators/remove")
	public ResponseEntity<Response> removeCollab(@RequestHeader("token") String token,@RequestParam("email") String email,@RequestParam("noteid") long noteId){
		NoteInformation noteInfo=userService.removeCollab(token,email,noteId);
		if(noteInfo != null)
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Collaborator is created successfully",  noteInfo));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Collaborator already exists",noteInfo));
	
	}
}
