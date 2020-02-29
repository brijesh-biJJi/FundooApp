package com.bridgelabz.fundoonotes.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.entity.Profile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProfileServiceImpl implements IProfileService {

	
	
	@Override
	public Profile uploadProfilePic(MultipartFile file, String originalFilename, String contentType, String token)
	{
		
		return null;
	}

}
