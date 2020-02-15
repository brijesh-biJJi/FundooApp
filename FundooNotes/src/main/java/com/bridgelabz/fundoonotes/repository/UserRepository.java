package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.entity.UserInformation;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
public interface UserRepository {


	UserInformation save(UserInformation info);
	
	UserInformation getUser(String name);

	boolean updateUserInfoIsVerifiedCol(Long userId);
	
	UserInformation fingUserById(Long id);

	List<UserInformation> getUsers();

	boolean updatePass(UpdatePassword passwordUpdateInfo, Long id);
}
