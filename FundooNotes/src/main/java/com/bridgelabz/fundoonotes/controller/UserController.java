package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;
import com.bridgelabz.fundoonotes.dto.LoginDto;
import com.bridgelabz.fundoonotes.dto.RegisterDto;
import com.bridgelabz.fundoonotes.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.Profile;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.IUserServices;
import com.bridgelabz.fundoonotes.utility.JWTGenerator;

import io.swagger.annotations.ApiOperation;


/**
 * 
 * @author Brijesh A Kanchan
 *@Purpose Controller class is to handle requests coming from the client. Then, the controller invokes a business class to process business-related tasks, and then redirects the client to a logical view name
 */
@RestController
@CrossOrigin("*")
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
	@ApiOperation(value="Register User", response = Response.class)
	//@CachePut(value="user", key="#token")
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
	@ApiOperation(value="Login User", response = Response.class)
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
	@ApiOperation(value="User Verification", response = Response.class)
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
	@ApiOperation(value="Forgot Password", response = Response.class)
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
	@ApiOperation(value="Update Password", response = Response.class)
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
	@ApiOperation(value="Get User", response = Response.class)
	//@Cacheable( value="users")
	public ResponseEntity<Response> getUsers() {
		List<User> userList = userService.getUsers();
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("List of all Users.", userList));
	}
	
	
	@GetMapping("user")
	@ApiOperation(value="Get User By Id", response = Response.class)
	//@Cacheable( value="users")
	public ResponseEntity<Response> getUserById(@RequestHeader("token") String token) {
		User user = userService.getUserById(token);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("User details", user));
	}
	
	/**
	 * API for adding collaborator
	 * @param token
	 * @param collabDtoInfo
	 * @param noteId
	 * @return
	 */
	@PostMapping("collaborator/add")
	@ApiOperation(value="Collaborate User", response = Response.class)
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
	@ApiOperation(value="Get Collaborated User", response = Response.class)
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
	@ApiOperation(value="Remove Collaborated  User", response = Response.class)
	public ResponseEntity<Response> removeCollab(@RequestHeader("token") String token,@RequestParam("email") String email,@RequestParam("noteid") long noteId){
		NoteInformation noteInfo=userService.removeCollab(token,email,noteId);
		if(noteInfo != null)
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Collaborator is created successfully",  noteInfo));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Collaborator already exists",noteInfo));
	
	}
	
	/**
	 * Profile
	 */
	/**
	 * API for uploading profile picture of specified User 
	 * @param file
	 * @param token
	 * @return
	 */
	@PostMapping("/uploadProfilePic")
	@ApiOperation(value = "Upload ProfilePic", response = Response.class)
	public ResponseEntity<Response> uploadProfilePic(@ModelAttribute MultipartFile file,
			@RequestHeader("token") String token) {
		Profile profileInfo = userService.uploadProfilePic(file, file.getOriginalFilename(), file.getContentType(),
				token);
		return profileInfo != null
				? ResponseEntity.status(HttpStatus.OK).body(new Response("Profile pic added succussefully", profileInfo))
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Profile pic not added", profileInfo));
	}
	
	/**
	 * API for removing profile picture of specified User
	 * @param token
	 * @return
	 */
	@DeleteMapping("/removeProfilePic")
	@ApiOperation(value = "Remove ProfilePic", response = Response.class)
	public ResponseEntity<Response> removeProfilePic(@RequestHeader("token") String token) {
		Profile profile = userService.removeProfilePic(token);
		return profile != null
				? ResponseEntity.status(HttpStatus.OK).body(new Response("Profile pic removed succussefully", profile))
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Profile pic not removed", profile));
	}
	
	/**
	 * API for retrieving profile picture of specified User
	 * @param token
	 * @return
	 */
	@GetMapping("/getprofilepic")
	@ApiOperation(value = "Retrieve ProfilePic", response = Response.class)
	public ResponseEntity<Response> getProfilePic(@RequestHeader("token") String token){
	
		S3Object profileInfo = 	userService.getProfilePic(token);
		return profileInfo!=null ?  ResponseEntity.status(HttpStatus.OK).body(new Response("Profile Pic Details....", profileInfo))
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("ProfilePic Not Found",profileInfo));
	}
	
	/**
	 * API for updating profile picture of specified User
	 * @param file
	 * @param token
	 * @return
	 */
	@PutMapping("/updateProfilepic")
	@ApiOperation(value = "Update ProfilePic", response = Response.class)
	public ResponseEntity<Response> updateProfilePic(@ModelAttribute MultipartFile file , @RequestHeader("token") String token){
		Profile profileInfo = userService.updateProfilePic(file, file.getOriginalFilename(), file.getContentType(),
				token);
		return profileInfo != null
				? ResponseEntity.status(HttpStatus.OK).body(new Response("ProfilePic updated succussefully",  profileInfo))
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("ProfilePic not updated ", profileInfo));
	}
}
