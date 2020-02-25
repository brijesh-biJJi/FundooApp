package com.bridgelabz.fundoonotes.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.bridgelabz.fundoonotes.dto.LoginDto;
import com.bridgelabz.fundoonotes.dto.RegisterDto;
import com.bridgelabz.fundoonotes.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exceptions.EmailNotFoundException;
import com.bridgelabz.fundoonotes.exceptions.NoteIdNotFoundException;
import com.bridgelabz.fundoonotes.exceptions.UserAlreadyExistsException;
import com.bridgelabz.fundoonotes.exceptions.UserNotFoundException;
import com.bridgelabz.fundoonotes.exceptions.UserNotVerifiedException;
import com.bridgelabz.fundoonotes.repository.INoteRepo;
import com.bridgelabz.fundoonotes.repository.IUserJpaRepo;
import com.bridgelabz.fundoonotes.repository.NoteRepoImpl;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.response.MailObject;
import com.bridgelabz.fundoonotes.response.MailResponse;
import com.bridgelabz.fundoonotes.utility.JWTGenerator;
import com.bridgelabz.fundoonotes.utility.MailServiceProvider;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserServices {

private User userInfo = new User();
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private IUserJpaRepo userJpaRepo;
	
	@Autowired
	private INoteRepo noteRepo;
	
	@Autowired
	private JWTGenerator jwtGenerate;
	@Autowired
	private BCryptPasswordEncoder encrypt;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private MailResponse response;
	@Autowired
	private MailObject mailObj;

	
	/**
	 * register method is used to Register the user
	 */
	@Transactional
	@Override
	public User register(RegisterDto registerDtoInfo) {
		/**
		 * Retrieving the UserInformation
		 */
		User user = userRepo.getUser(registerDtoInfo.getEmail());
		if (user == null) 
		{
			userInfo = modelMapper.map(registerDtoInfo, User.class);
			userInfo.setDateTime(LocalDateTime.now());
			String epass = encrypt.encode(registerDtoInfo.getPassword());
			userInfo.setPassword(epass);
			userInfo.setVerified(false);
			
			/**
			 * Saving the USer Information into DB
			 */
			userInfo = userRepo.save(userInfo);
			
			
			String mailResponse = response.mergeMsg("http://localhost:8080/users/verify",jwtGenerate.createToken(userInfo.getUserid()));
			log.info(mailResponse);

			mailObj.setEmail(registerDtoInfo.getEmail());
			mailObj.setSubject("Verification");
			mailObj.setMessage(mailResponse);
			
			
			MailServiceProvider.sendEmail(mailObj.getEmail(), mailObj.getSubject(), mailObj.getMessage());
			return userInfo;
		} 
		else
			throw new UserAlreadyExistsException("User Already Exists..");
	}
	
	/**
	 * login method is used to check whether its a Valid User 
	 * @throws EmailNotFoundException 
	 */

	@Transactional
	@Override
	public User login(LoginDto loginDtoInfo) 
	{
		/**
		 * Retrieving the UserInformation
		 */
		User userInf = userRepo.getUser(loginDtoInfo.getEmail());
		if(userInfo != null)
		{
			if(userInf.isVerified()==true && (encrypt.matches(loginDtoInfo.getPassword(), userInf.getPassword())))
				return userInf;
			else
			{
				String mailResponse = response.mergeMsg("http://localhost:8080/users/verify",jwtGenerate.createToken(userInf.getUserid()));
				log.info(mailResponse);
				mailObj.setEmail(loginDtoInfo.getEmail());
				mailObj.setSubject("Verification");
				mailObj.setMessage(mailResponse);
				
				
				MailServiceProvider.sendEmail(mailObj.getEmail(), mailObj.getSubject(), mailObj.getMessage());
				
				throw new UserNotVerifiedException("Invalid credentials..");
			}
		}
		else
		{
			throw new EmailNotFoundException(loginDtoInfo.getEmail()+" Please register before login");
		}
	}

	/**
	 * updateIsVerify method is used to Update the record in the Database
	 */
	@Transactional
	@Override
	public boolean updateIsVerify(String token) {
		log.info("After Decrypt Token :"+(long) jwtGenerate.parseToken(token));
		Long userId=(long) jwtGenerate.parseToken(token);
		userRepo.updateUserInfoIsVerifiedCol(userId);
		return true;
	}

	/**
	 * This method is used to retrieve Users from Database
	 */
	@Transactional
	@Override
	public List<User> getUsers() {
		List<User> usersList = userRepo.getUsers();
		return usersList;
	}

	/**
	 * This method is used to check whether User Exists or not
	 */
	@Override
	public boolean isUserExist(String email) {
		try {
			User userInfo = userRepo.getUser(email);
			if (userInfo.isVerified() == true) 
			{
				String mailResposne = response.mergeMsg("http://localhost:8080/verify",jwtGenerate.createToken(userInfo.getUserid()));
				MailServiceProvider.sendEmail(userInfo.getEmail(), "Verification", mailResposne);
				return true;
			} 
		} catch (Exception e) {
			log.info(e.getMessage());;
		}
		return false;
	}

	/**
	 * Method is used to Update the Password
	 */
	@Transactional
	@Override
	public boolean updatePassword(UpdatePassword passwordUpdateInfo, String token) {
		Long id = null;
		try {
			id = (long)jwtGenerate.parseToken(token);
			String epassword = encrypt.encode(passwordUpdateInfo.getConfirmPassword());
			passwordUpdateInfo.setConfirmPassword(epassword);
			return userRepo.updatePass(passwordUpdateInfo, id);

		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return false;
	}

	/**
	 * Method is used to collaborate the User
	 */
	@Transactional
	@Override
	public NoteInformation addCollab(String token, String email, long noteId)
	{
		long userId=jwtGenerate.parseToken(token);
		userInfo = userRepo.findUserById(userId);
		if (userInfo != null)
		{
			User collabUser=userRepo.getUser(email);
			if(collabUser != null)
			{
				NoteInformation noteInfo=noteRepo.findNoteById(noteId);
				if(noteInfo != null)
				{
					//collabUser.getCollabList().add(noteInfo);
					noteInfo.getCollabList().add(collabUser);
					return noteInfo;
				}
				else 
					throw new NoteIdNotFoundException("Note Id Not Found..!");
			}
			else
				throw new UserNotFoundException("User not found..!");
		}
		else
			throw new UserNotFoundException("User not found..!");
	}

	/**
	 * Method is used to get Collaborator List
	 */
	@Transactional
	@Override
	public List<User> getCollab(String token,long noteId) 
	{
		long userId=jwtGenerate.parseToken(token);
		userInfo = userRepo.findUserById(userId);
		if (userInfo != null)
		{
			NoteInformation noteInfo=noteRepo.findNoteById(noteId);
			if(noteInfo !=null)
			{
				List<User> collabList=noteInfo.getCollabList();
				return collabList;
			}
			else 
				throw new NoteIdNotFoundException("Note Id Not Found..!");
		}
		else
			throw new UserNotFoundException("User not found..!");
	
	}

	@Transactional
	@Override
	public NoteInformation removeCollab(String token, String email, long noteId) {
		long userId=jwtGenerate.parseToken(token);
		userInfo = userRepo.findUserById(userId);
		if (userInfo != null)
		{
			User collabUser=userRepo.getUser(email);
			if(collabUser != null)
			{
				NoteInformation noteInfo=noteRepo.findNoteById(noteId);
				if(noteInfo != null)
				{
					//collabUser.getCollabList().add(noteInfo);
					noteInfo.getCollabList().remove(collabUser);
					return noteInfo;
				}
				else 
					throw new NoteIdNotFoundException("Note Id Not Found..!");
			}
			else
				throw new UserNotFoundException("User not found..!");
		}
		else
			throw new UserNotFoundException("User not found..!");
	}


}
