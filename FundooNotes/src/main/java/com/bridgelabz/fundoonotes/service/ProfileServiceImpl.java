package com.bridgelabz.fundoonotes.service;

import java.io.IOException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.bridgelabz.fundoonotes.entity.Profile;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exceptions.ProfileNotFoundException;
import com.bridgelabz.fundoonotes.exceptions.UserNotFoundException;
import com.bridgelabz.fundoonotes.repository.ProfileRepo;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.utility.JWTGenerator;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
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
	
	/**
	 * Method is used to Upload ProfilePic Specified User
	 */
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
			throw new UserNotFoundException("User not found..!",HttpStatus.NOT_FOUND);
		return null;
	}

	
	/**
	 * Method is used to Retrieving the ProfilePic of Specified User
	 */
	@Override
	public S3Object getProfilePic(String token) {
		long userId=jwtGenerator.parseToken(token);
		User userInfo=userRepo.findUserById(userId);
		if(userInfo != null)
		{
			Profile profileInfo=profileRepo.findByUserId(userId);
			if(profileInfo != null)
			{
				S3Object s3Object;
				try 
				{
					s3Object = amazonS3.getObject(new GetObjectRequest(bucketName, profileInfo.getProfilePicName()));
				} 
				catch (AmazonServiceException serviceException) {
					throw new RuntimeException("Error while streaming File.");
				} 
				catch (AmazonClientException exception) {
					throw new RuntimeException("Error while streaming File.");
				}
				return s3Object;
			}
			else
				throw new ProfileNotFoundException("Profile Not Found..");
		}
		else
			throw new UserNotFoundException("User not found..!",HttpStatus.NOT_FOUND);
	}

	/**
	 * Method is used to Update the ProfilePic od Specified User
	 */
	@Transactional
	@Override
	public Profile updateProfilePic(MultipartFile file, String originalFilename, String contentType, String token) {
		long userId=jwtGenerator.parseToken(token);
		User userInfo=userRepo.findUserById(userId);
		if(userInfo != null)
		{
			Profile profileInfo=profileRepo.findByUserId(userId);
			if(profileInfo != null)
			{
				ObjectMetadata objectMetaData=new ObjectMetadata();
				objectMetaData.setContentType(contentType);
				objectMetaData.setContentLength(file.getSize());
				 
				try {
					amazonS3.putObject(bucketName, originalFilename, file.getInputStream(), objectMetaData);
					int val=profileRepo.updateByUserId(originalFilename, userId);
					if(val>0) {
						Profile profileInfo1=profileRepo.findByUserId(userId);
						return profileInfo1;
					}
				} catch (AmazonClientException | IOException e) {
					e.printStackTrace();
				}
			}
			else
				throw new ProfileNotFoundException("Profile Not Found..");
		}
		else
			throw new UserNotFoundException("User not found..!",HttpStatus.NOT_FOUND);
		return null;		
	}

	/**
	 * Method is used to Remove the ProfilePic of Specified User
	 */
	@Transactional
	@Override
	public Profile removeProfilePic(String token) {
		long userId=jwtGenerator.parseToken(token);
		User userInfo=userRepo.findUserById(userId);
		if(userInfo != null)
		{
			Profile profileInfo=profileRepo.findByUserId(userId);
			if(profileInfo != null)
			{
				try {
					amazonS3.deleteObject(bucketName, profileInfo.getProfilePicName());
				} catch (AmazonServiceException serviceException) {
					log.error(serviceException.getErrorMessage());
				} catch (AmazonClientException clientException) {
					log.error(clientException.getMessage());
				}
				profileRepo.deleteByUserId(userId);	
				return profileInfo;
			}
			else
				throw new ProfileNotFoundException("Profile Not Found..");
		}
		else
			throw new UserNotFoundException("User not found..!",HttpStatus.NOT_FOUND);
	}

}
