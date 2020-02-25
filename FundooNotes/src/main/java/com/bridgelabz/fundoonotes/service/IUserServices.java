package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.LoginDto;
import com.bridgelabz.fundoonotes.dto.RegisterDto;
import com.bridgelabz.fundoonotes.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.entity.User;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
public interface IUserServices {

	User register(RegisterDto info);
	
	User login(LoginDto info);

	boolean updateIsVerify(String token);

	List<User> getUsers();

	boolean isUserExist(String email);

	boolean updatePassword(UpdatePassword passwordUpdateInfo, String token);

	User addCollab(String token, String email, long noteId);

	
	
	
}
