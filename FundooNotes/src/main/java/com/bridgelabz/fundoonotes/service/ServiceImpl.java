package com.bridgelabz.fundoonotes.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.LoginDto;
import com.bridgelabz.fundoonotes.dto.RegisterDto;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.response.MailObject;
import com.bridgelabz.fundoonotes.response.MailResponse;
import com.bridgelabz.fundoonotes.utility.JWTGenerator;
import com.bridgelabz.fundoonotes.utility.MailServiceProvider;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */

@Service
public class ServiceImpl implements Services {

private UserInformation userInfo = new UserInformation();
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private JWTGenerator jwtGenerate;
	@Autowired
	private BCryptPasswordEncoder encrypt;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private MailResponse response;
	@Autowired
	private MailObject mailObj;

	
	/**
	 * register method is used to Register the user
	 */
	@Transactional
	@Override
	public boolean register(RegisterDto registerDtoInfo) {
		System.out.println("1st lin of servimpl");
		
		/**
		 * Retrieving the UserInformation
		 */
		UserInformation user = userRepo.getUser(registerDtoInfo.getEmail());
		if (user == null) {
			System.out.println("2nd lin of servimpl");
			userInfo = modelMapper.map(registerDtoInfo, UserInformation.class);
			userInfo.setDateTime(LocalDateTime.now());
			String epass = encrypt.encode(registerDtoInfo.getPassword());
			userInfo.setPassword(epass);
			userInfo.setVerified(false);
			
			/**
			 * Saving the USer Information into DB
			 */
			userInfo = userRepo.save(userInfo);
			
			
			String mailResponse = response.fromMsg("http://localhost:8080/verify",jwtGenerate.encryptToken(userInfo.getUserid()));
			System.out.println(mailResponse);
			mailObj.setEmail(registerDtoInfo.getEmail());
			mailObj.setMessage(mailResponse);
			mailObj.setSubject("Verification");
			
			MailServiceProvider.sendEmail(mailObj.getEmail(), mailObj.getSubject(), mailObj.getMessage());
			System.out.println("inReg");
			return true;
		} 
		System.out.println("outReg");
		return false;
	}

	@Transactional
	@Override
	public UserInformation login(LoginDto loginDtoInfo) 
	{
		/**
		 * Retrieving the UserInformation
		 */
		UserInformation userInfo = userRepo.getUser(loginDtoInfo.getEmail());
		if(userInfo != null)
		{
			
		}
		return null;
	}

	@Transactional
	@Override
	public boolean updateIsVerify(String token) {
		System.out.println("Id Verification :"+(long) jwtGenerate.decryptToken(token));
		Long userId=(long) jwtGenerate.decryptToken(token);
		userRepo.updateUserInfoIsVerifiedCol(userId);
		return true;
	}

}
