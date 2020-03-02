package com.bridgelabz.fundoonotes.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.bridgelabz.fundoonotes.entity.Profile;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exceptions.UserNotFoundException;
import com.bridgelabz.fundoonotes.repository.ProfileRepo;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.utility.JWTGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProfileServiceImpl implements IProfileService {

	@Autowired
	private ProfileRepo profileRepo;
	
	@Autowired
	private JWTGenerator jwtGenerator;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired 
	private AmazonS3 amazonS3;
	
	@Value("${aws.bucketName}")
	private String bucketName;
	
	@Override
	public Profile uploadProfilePic(MultipartFile file, String originalFilename, String contentType, String token)
	{
		long userId=jwtGenerator.parseToken(token);
		User userInfo=userRepo.findUserById(userId);
		if(userInfo != null)
		{
			Profile profileInfo=new Profile(originalFilename,userInfo);
			ObjectMetadata objectMetaData=new ObjectMetadata();
			objectMetaData.setContentType(contentType);
			objectMetaData.setContentLength(file.getSize());
			 
			try {
				amazonS3.putObject(bucketName, originalFilename, file.getInputStream(), objectMetaData);
				profileRepo.save(profileInfo);
				return profileInfo;
			} catch (AmazonClientException | IOException e) {
				e.printStackTrace();
			}
		}
		else
			throw new UserNotFoundException("User Not found.");
		return null;
	}

	@Override
	public Profile removeProfilePic(String token) {
		long userId=jwtGenerator.parseToken(token);
		User userInfo=userRepo.findUserById(userId);
		if(userInfo != null)
		{
		}
		else
			throw new UserNotFoundException("User Not found.");
		return null;
	}

}
