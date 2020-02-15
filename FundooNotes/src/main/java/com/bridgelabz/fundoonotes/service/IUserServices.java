package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.LoginDto;
import com.bridgelabz.fundoonotes.dto.RegisterDto;
import com.bridgelabz.fundoonotes.entity.UserInformation;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
public interface IUserServices {

	boolean register(RegisterDto info);
	
	UserInformation login(LoginDto info);

	boolean updateIsVerify(String token);
	
	
}
