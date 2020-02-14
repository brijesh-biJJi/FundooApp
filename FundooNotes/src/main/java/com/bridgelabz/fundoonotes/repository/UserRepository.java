package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.PasswordUpdate;
import com.bridgelabz.fundoonotes.entity.UserInformation;

public interface UserRepository {


	UserInformation save(UserInformation info);
	
	UserInformation getUser(String name);

	void updateUserInfoIsVerifiedCol(Long userId);
}
