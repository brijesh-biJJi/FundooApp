package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.entity.User;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
public interface UserRepository {


	User save(User info);
	
	User getUser(String email);

	boolean updateUserInfoIsVerifiedCol(Long userId);
	
	User findUserById(Long id);

	List<User> getUsers();

	boolean updatePass(UpdatePassword passwordUpdateInfo, Long id);
}
