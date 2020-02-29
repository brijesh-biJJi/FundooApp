package com.bridgelabz.fundoonotes.service;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.entity.Profile;

public interface IProfileService {

	Profile uploadProfilePic(MultipartFile file, String originalFilename, String contentType, String token);

}
