package com.bridgelabz.fundoonotes.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.LoginInformation;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.response.MailObject;
import com.bridgelabz.fundoonotes.response.MailResponse;
import com.bridgelabz.fundoonotes.utility.JWTGenerator;
import com.bridgelabz.fundoonotes.utility.MailServiceProvider;

@Service
public class ServiceImpl implements Services {

private UserInformation userInfo = new UserInformation();
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private JWTGenerator jwtGenerate;
	@Autowired
	private BCryptPasswordEncoder encrypt;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private MailResponse response;
	@Autowired
	private MailObject mailObject;

	
	@Transactional
	@Override
	public boolean register(UserDto info) {
		System.out.println("1st lin of servimpl");
		UserInformation user = repository.getUser(info.getEmail());
		if (user == null) {
			System.out.println("2nd lin of servimpl");
			userInfo = modelMapper.map(info, UserInformation.class);
			userInfo.setDateTime(LocalDateTime.now());
			String epass = encrypt.encode(info.getPassword());
			userInfo.setPassword(epass);
			userInfo.setVerified(false);
			userInfo = repository.save(userInfo);
			String mailResponse = response.fromMsg("http://localhost:8080/verify",jwtGenerate.encryptToken(userInfo.getUserid()));

			mailObject.setEmail(info.getEmail());
			mailObject.setMessage(mailResponse);
			mailObject.setSubject("Verification");
			MailServiceProvider.sendEmail(mailObject.getEmail(), mailObject.getSubject(), mailObject.getMessage());
			System.out.println("inReg");
		} 
		System.out.println("outReg");
		return true;
	}

	@Override
	public UserInformation login(LoginInformation info) {
		// TODO Auto-generated method stub
		return null;
	}

}
