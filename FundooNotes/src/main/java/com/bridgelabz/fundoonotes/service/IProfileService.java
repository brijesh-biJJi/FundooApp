package com.bridgelabz.fundoonotes.service;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;
import com.bridgelabz.fundoonotes.entity.Profile;

public interface IProfileService {

	Profile uploadProfilePic(MultipartFile file, String originalFilename, String contentType, String token);

	//Profile removeProfilePic(String token);

	S3Object getProfilePic(String token);

}
