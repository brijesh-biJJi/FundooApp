package com.bridgelabz.fundoonotes.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
//
//import com.amazonaws.AmazonClientException;
//import com.amazonaws.AmazonServiceException;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.GetObjectRequest;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.S3Object;
import com.bridgelabz.fundoonotes.config.RabbitMQSender;
import com.bridgelabz.fundoonotes.dto.LoginDto;
import com.bridgelabz.fundoonotes.dto.RegisterDto;
import com.bridgelabz.fundoonotes.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.Profile;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exceptions.EmailNotFoundException;
import com.bridgelabz.fundoonotes.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.exceptions.ProfileNotFoundException;
import com.bridgelabz.fundoonotes.exceptions.UserAlreadyExistsException;
import com.bridgelabz.fundoonotes.exceptions.UserNotFoundException;
import com.bridgelabz.fundoonotes.exceptions.UserNotVerifiedException;
import com.bridgelabz.fundoonotes.repository.INoteRepo;
import com.bridgelabz.fundoonotes.repository.IUserJpaRepo;
import com.bridgelabz.fundoonotes.repository.NoteRepoImpl;
import com.bridgelabz.fundoonotes.repository.UserJpaRepo;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.response.MailObject;
import com.bridgelabz.fundoonotes.response.MailResponse;
import com.bridgelabz.fundoonotes.response.Response;
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

	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	
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

	@Autowired
	private RabbitMQSender rabbitMqSender;
	
	@Autowired
	private UserJpaRepo profileRepo;
	
//	@Autowired 
//	private AmazonS3 amazonS3;
	
//	@Value("${aws.bucketName}")
//	private String bucketName;
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
			
			//rabbitMqSender.send(mailObj);
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
				//String mailResposne = response.mergeMsg("http://localhost:8080/verify",jwtGenerate.createToken(userInfo.getUserid()));
				String mailResponse=response.sendUrl("FundooNotes Verification link... http://localhost:4200/changePassword");
				MailServiceProvider.sendEmail(userInfo.getEmail(), "FundooNotes Verification", mailResponse);
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
		long userId=jwtGenerate.parseToken(token);
		userInfo = userRepo.findUserById(userId);
		if (userInfo != null)
		{
			String epassword = encrypt.encode(passwordUpdateInfo.getConfirmPassword());
			passwordUpdateInfo.setConfirmPassword(epassword);
			return userRepo.updatePass(passwordUpdateInfo, userId);
		}
		else
			throw new UserNotFoundException("User not found..!",HttpStatus.NOT_FOUND);
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
					throw new NoteNotFoundException("Note not found..!",HttpStatus.NOT_FOUND);
			}
			else
				throw new UserNotFoundException("User not found..!",HttpStatus.NOT_FOUND);
		}
		else
			throw new UserNotFoundException("User not found..!",HttpStatus.NOT_FOUND);
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
				throw new NoteNotFoundException("Note not found..!",HttpStatus.NOT_FOUND);
		}
		else
			throw new UserNotFoundException("User not found..!",HttpStatus.NOT_FOUND);
	
	}

	
	/**
	 * Method is used to remove the specified Collaborator
	 */
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
					noteInfo.getCollabList().remove(collabUser);
					return noteInfo;
				}
				else 
					throw new NoteNotFoundException("Note not found..!",HttpStatus.NOT_FOUND);
			}
			else
				throw new UserNotFoundException("User not found..!",HttpStatus.NOT_FOUND);
		}
		else
			throw new UserNotFoundException("User not found..!",HttpStatus.NOT_FOUND);
	}

	/**
	 * Method is used to retrieve USer by ID
	 */
	@Override
	public User getUserById(String token)
	{
		long userId=jwtGenerate.parseToken(token);
		userInfo = userRepo.findUserById(userId);
		if (userInfo != null)
		{
			return userInfo;
		}
		else
			throw new UserNotFoundException("User not found..!",HttpStatus.NOT_FOUND);
	}

	@Override
	public Profile uploadProfilePic(MultipartFile file, String originalFilename, String contentType, String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Profile removeProfilePic(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Profile updateProfilePic(MultipartFile file, String originalFilename, String contentType, String token) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	/**
	 * Method is used to Upload ProfilePic Specified User
	 */
//	@Override
//	public Profile uploadProfilePic(MultipartFile file, String originalFilename, String contentType, String token)
//	{
//		long userId=jwtGenerate.parseToken(token);
//		User userInfo=userRepo.findUserById(userId);
//		if(userInfo != null)
//		{
//			Profile profileInfo=new Profile(originalFilename,userInfo);
//			ObjectMetadata objectMetaData=new ObjectMetadata();
//			objectMetaData.setContentType(contentType);
//			objectMetaData.setContentLength(file.getSize());
//			 
//			try {
//				amazonS3.putObject(bucketName, originalFilename, file.getInputStream(), objectMetaData);
//				profileRepo.save(profileInfo);
//				return profileInfo;
//			} catch (AmazonClientException | IOException e) {
//				e.printStackTrace();
//			}
//		}
//		else
//			throw new UserNotFoundException("User not found..!",HttpStatus.NOT_FOUND);
//		return null;
//	}
	
//	/**
//	 * Method is used to Remove the ProfilePic of Specified User
//	 */
//	@Transactional
//	@Override
//	public Profile removeProfilePic(String token) {
//		long userId=jwtGenerate.parseToken(token);
//		User userInfo=userRepo.findUserById(userId);
//		if(userInfo != null)
//		{
//			Profile profileInfo=profileRepo.findByUserId(userId);
//			if(profileInfo != null)
//			{
//				try {
//					amazonS3.deleteObject(bucketName, profileInfo.getProfilePicName());
//				} catch (AmazonServiceException serviceException) {
//					log.error(serviceException.getErrorMessage());
//				} catch (AmazonClientException clientException) {
//					log.error(clientException.getMessage());
//				}
//				profileRepo.deleteByUserId(userId);	
//				return profileInfo;
//			}
//			else
//				throw new ProfileNotFoundException("Profile Not Found..");
//		}
//		else
//			throw new UserNotFoundException("User not found..!",HttpStatus.NOT_FOUND);
//	}
//
//	/**
//	 * Method is used to Retrieving the ProfilePic of Specified User
//	 */
//	@Override
//	public S3Object getProfilePic(String token) {
//		long userId=jwtGenerate.parseToken(token);
//		User userInfo=userRepo.findUserById(userId);
//		if(userInfo != null)
//		{
//			Profile profileInfo=profileRepo.findByUserId(userId);
//			if(profileInfo != null)
//			{
//				S3Object s3Object;
//				try 
//				{
//					s3Object = amazonS3.getObject(new GetObjectRequest(bucketName, profileInfo.getProfilePicName()));
//				} 
//				catch (AmazonServiceException serviceException) {
//					throw new RuntimeException("Error while streaming File.");
//				} 
//				catch (AmazonClientException exception) {
//					throw new RuntimeException("Error while streaming File.");
//				}
//				return s3Object;
//			}
//			else
//				throw new ProfileNotFoundException("Profile Not Found..");
//		}
//		else
//			throw new UserNotFoundException("User not found..!",HttpStatus.NOT_FOUND);
//	}
//	
//	/**
//	 * Method is used to Update the ProfilePic od Specified User
//	 */
//	@Transactional
//	@Override
//	public Profile updateProfilePic(MultipartFile file, String originalFilename, String contentType, String token) {
//		long userId=jwtGenerate.parseToken(token);
//		User userInfo=userRepo.findUserById(userId);
//		if(userInfo != null)
//		{
//			Profile profileInfo=profileRepo.findByUserId(userId);
//			if(profileInfo != null)
//			{
//				ObjectMetadata objectMetaData=new ObjectMetadata();
//				objectMetaData.setContentType(contentType);
//				objectMetaData.setContentLength(file.getSize());
//				 
//				try {
//					amazonS3.putObject(bucketName, originalFilename, file.getInputStream(), objectMetaData);
//					int val=profileRepo.updateByUserId(originalFilename, userId);
//					if(val>0) {
//						Profile profileInfo1=profileRepo.findByUserId(userId);
//						return profileInfo1;
//					}
//				} catch (AmazonClientException | IOException e) {
//					e.printStackTrace();
//				}
//			}
//			else
//				throw new ProfileNotFoundException("Profile Not Found..");
//		}
//		else
//			throw new UserNotFoundException("User not found..!",HttpStatus.NOT_FOUND);
//		return null;		
//	}


}
