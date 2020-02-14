package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.LoginDto;
import com.bridgelabz.fundoonotes.dto.RegisterDto;
import com.bridgelabz.fundoonotes.entity.UserInformation;

public interface Services {

	boolean register(RegisterDto info);
	
	UserInformation login(LoginDto info);
	
	
}
