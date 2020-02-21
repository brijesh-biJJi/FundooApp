package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.LoginDto;
import com.bridgelabz.fundoonotes.dto.RegisterDto;
import com.bridgelabz.fundoonotes.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.entity.UserInformation;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
public interface IUserServices {

	UserInformation register(RegisterDto info);
	
	UserInformation login(LoginDto info);

	boolean updateIsVerify(String token);

	List<UserInformation> getUsers();

	boolean isUserExist(String email);

	boolean updatePassword(UpdatePassword passwordUpdateInfo, String token);
	
	
}
