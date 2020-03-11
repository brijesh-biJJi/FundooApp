package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.LoginDto;
import com.bridgelabz.fundoonotes.dto.RegisterDto;
import com.bridgelabz.fundoonotes.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
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

	NoteInformation addCollab(String token, String email, long noteId);

	List<User> getCollab(String token, long noteId);

	NoteInformation removeCollab(String token, String email,long noteId);

	User getUserById(String token);

	
	
	
}
