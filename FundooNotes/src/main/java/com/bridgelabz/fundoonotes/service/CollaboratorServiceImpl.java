package com.bridgelabz.fundoonotes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.CollaboratorDto;
import com.bridgelabz.fundoonotes.entity.CollaboratorInformation;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exceptions.UserNotFoundException;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.utility.JWTGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CollaboratorServiceImpl implements ICollaboratorService 
{
	@Autowired
	JWTGenerator jwtGenerator;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	User userInfo;
	
	@Override
	public CollaboratorInformation createCollab(String token, CollaboratorDto collabDtoInfo, long noteId) {
		long userId=jwtGenerator.parseToken(token);
		userInfo = userRepo.findUserById(userId);
		if (userInfo != null)
		{
			log.info("ceck "+userInfo.getName());
			
		}
		else
			throw new UserNotFoundException("User not found..!");
		return null;
	}

}
