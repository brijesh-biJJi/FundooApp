package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;
import com.bridgelabz.fundoonotes.entity.Profile;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.IProfileService;

import io.swagger.annotations.ApiOperation;

@RestController
public class ProfileController {
	
	@Autowired
	private IProfileService profileService;

	/**
	 * Api for uploading profile picture of User 
	 * @param file
	 * @param token
	 * @return
	 */
	@PostMapping("/uploadProfilePic")
	@ApiOperation(value = "Upload ProfilePic", response = Response.class)
	public ResponseEntity<Response> uploadProfilePic(@ModelAttribute MultipartFile file,
			@RequestHeader("token") String token) {
		Profile profileInfo = profileService.uploadProfilePic(file, file.getOriginalFilename(), file.getContentType(),
				token);
		return profileInfo.getUserInfo() != null
				? ResponseEntity.status(HttpStatus.OK).body(new Response("Profile pic added succussefully", profileInfo))
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Profile pic not added", profileInfo));
	}
	
//	@DeleteMapping("/removeProfilePic")
//	@ApiOperation(value = "Remove ProfilePic", response = Response.class)
//	public ResponseEntity<Response> removeProfilePic(@RequestHeader("token") String token) {
//		Profile profile = profileService.removeProfilePic(token);
//		return profile.getUserInfo() == null
//				? ResponseEntity.status(HttpStatus.OK).body(new Response("Profile pic removed succussefully", profile))
//				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Profile pic not removed", profile));
//	}
	
	@GetMapping("/getprofilepic")
	@ApiOperation(value = "Retrieve ProfilePic", response = Response.class)
	public ResponseEntity<Response> getProfilePic(@RequestHeader("token") String token){
	
		S3Object profileInfo = 	profileService.getProfilePic(token);
		return profileInfo!=null ?  ResponseEntity.status(HttpStatus.OK).body(new Response("profile pic", profileInfo))
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("no profile pic ",profileInfo));
	}
}
