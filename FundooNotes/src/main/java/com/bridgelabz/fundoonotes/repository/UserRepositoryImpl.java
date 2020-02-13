package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.PasswordUpdate;
import com.bridgelabz.fundoonotes.entity.UserInformation;

public class UserRepositoryImpl implements UserRepository {

	@Override
	public UserInformation save(UserInformation info) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserInformation getUser(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserInformation getUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updatePassword(PasswordUpdate info, Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean verify(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<UserInformation> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

}
