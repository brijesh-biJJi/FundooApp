package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.LoginInformation;
import com.bridgelabz.fundoonotes.dto.UserDto;
import com.bridgelabz.fundoonotes.entity.UserInformation;

public interface Services {

	boolean register(UserDto info);
	
	UserInformation login(LoginInformation info);
	
	
}
