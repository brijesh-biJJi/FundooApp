package com.bridgelabz.fundoonotes.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.EditLabel;
import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.entity.LabelInformation;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exceptions.LabelNotFoundException;
import com.bridgelabz.fundoonotes.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.exceptions.UserNotFoundException;
import com.bridgelabz.fundoonotes.repository.ILabelJpaRepo;
import com.bridgelabz.fundoonotes.repository.ILabelRepo;
import com.bridgelabz.fundoonotes.repository.INoteRepo;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.utility.JWTGenerator;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
@Slf4j
@Service
@Transactional
public class LabelServiceImpl implements ILabelService {

	@Autowired
	private ILabelRepo labelRepo;
	
	@Autowired
	private ILabelJpaRepo labelJpaRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private JWTGenerator jwtGenerator;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private INoteRepo noteRepo;
	
	private User userInfo=new User();
	
	private NoteInformation noteInfo=new NoteInformation();
	
	/**
	 * Method is used to create Label
	 */
	@Override
	public LabelInformation createLabel(String token, LabelDto labelDtoInfo)
	{
		long userId=jwtGenerator.parseToken(token);
		userInfo = userRepo.findUserById(userId);
		if (userInfo != null)
		{
			LabelInformation labelInfo=labelJpaRepo.findByName(labelDtoInfo.getLabelName(),userInfo.getUserid());
			if(labelInfo == null)
			{
				labelInfo=modelMapper.map(labelDtoInfo, LabelInformation.class);
				labelInfo.getLabelId();
				labelInfo.getLabelName();
				labelInfo.setUserId(userInfo.getUserid());
				labelJpaRepo.save(labelInfo);
				return labelInfo;
			}
		}
		else
			throw new UserNotFoundException("User not found..!",HttpStatus.NOT_FOUND);
		return null;
	}


	/**
	 * Method is used to Add Label to Note if and only if Label is present OR Create the Label and Add to Note
	 */
	@Override
	public LabelInformation addMapLabel(String token, LabelDto labelDtoInfo, long noteId) {
		
		long userId=jwtGenerator.parseToken(token);
		userInfo = userRepo.findUserById(userId);
		if (userInfo != null)
		{
			NoteInformation noteInfo=noteRepo.findNoteById(noteId);
			if(noteInfo != null)
			{
				LabelInformation labelInfo=labelJpaRepo.findByName(labelDtoInfo.getLabelName(),userInfo.getUserid());
				if(labelInfo!=null)
				{
					labelInfo.getNotelist().add(noteInfo);
					labelJpaRepo.save(labelInfo);
					return labelInfo;
				}
				else
				{
					/**
					 * Creating the Label
					 */
					LabelInformation labelInfo1=createLabel(token, labelDtoInfo);
					
					/**
					 * Adding the Label into Note
					 */
					noteInfo.getLabelList().add(labelInfo1);
					noteRepo.saveNote(noteInfo);
					labelJpaRepo.save(labelInfo1);
					return labelInfo1;
				}
			}
			else
				throw new NoteNotFoundException("Note not found..!",HttpStatus.NOT_FOUND);
		}
		else
			throw new UserNotFoundException("User not found..!",HttpStatus.NOT_FOUND);
		
	}


	/**
	 * Method is used to remove the Label from Note
	 */
	@Override
	public LabelInformation removeNoteLabel(String token, LabelDto labelDtoInfo, long noteId) {
		
		long userId=jwtGenerator.parseToken(token);
		userInfo = userRepo.findUserById(userId);
		if (userInfo != null)
		{
			NoteInformation noteInfo=noteRepo.findNoteById(noteId);
			if(noteInfo != null)
			{
				LabelInformation labelInfo=labelJpaRepo.findByName(labelDtoInfo.getLabelName(),userInfo.getUserid());
				if(labelInfo!=null)
				{
					noteInfo.getLabelList().remove(labelInfo);
					noteRepo.saveNote(noteInfo);
					return labelInfo;
				}
				else
				{
					return labelInfo;
				}
			}
			else
				throw new NoteNotFoundException("Note not found..!",HttpStatus.NOT_FOUND);
		}
		else
			throw new UserNotFoundException("User not found..!",HttpStatus.NOT_FOUND);
		
	}


	/**
	 * Method is used to delete the Label from User as well as from Note
	 */
	@Override
	public boolean deleteUserLabel(String token, String labelName) 
	{
		long userId=jwtGenerator.parseToken(token);
		userInfo = userRepo.findUserById(userId);
		if (userInfo != null)
		{
//			NoteInformation noteInfo=noteRepo.findNoteById(noteId);
//			if(noteInfo != null)
//			{
			System.out.println("delete label "+labelName);
				LabelInformation labelInfo=labelJpaRepo.findByName(labelName,userInfo.getUserid());
				if(labelInfo!=null)
				{
				System.out.println("delete label "+labelInfo.getLabelName());	
//					noteInfo.getLabelList().remove(labelInfo);
					//delete from label_note where (label_id) in (select label_id from labelinfo where label_name=?)
//					noteRepo.saveNote(noteInfo);
					labelJpaRepo.deleteByName(labelInfo.getLabelName());
					return true;
				}
				else
					throw new LabelNotFoundException("Label Not Found...",HttpStatus.NOT_FOUND);
//			}
//			else
//				throw new NoteNotFoundException("Note not found..!",HttpStatus.NOT_FOUND);
		}
		else
			throw new UserNotFoundException("User not found..!",HttpStatus.NOT_FOUND);
	}

	/**
	 * Method is used to edit Label Information 
	 */
	@Override
	public LabelInformation editLabel(String token, EditLabel editLabelInfo) 
	{
		long userId=jwtGenerator.parseToken(token);
		userInfo=userRepo.findUserById(userId);
		if(userInfo != null)
		{
			Optional<LabelInformation> labelInfo=labelJpaRepo.findById(editLabelInfo.getLabelId());
			if(labelInfo.isPresent())
			{
				int val=labelJpaRepo.updateById(editLabelInfo.getLabelId(),editLabelInfo.getLabelName());
				if(val > 0) {
					LabelInformation labelInf=modelMapper.map(editLabelInfo, LabelInformation.class);
					labelInf.getLabelId();
					labelInf.getLabelName();
					labelInf.setUserId(userInfo.getUserid());
					return labelInf;
				}
			}
			else
				throw new LabelNotFoundException("Label Not Found...",HttpStatus.NOT_FOUND);
			
		}
		else
			throw new UserNotFoundException("User not found..!",HttpStatus.NOT_FOUND);
		return null;
		
	}


	/**
	 * Method is used to retrieve all Notes associated with Particular Label
	 */
	@Override
	public List<NoteInformation> retrieveNotes(String token, String labelName) {
		
		long userId=jwtGenerator.parseToken(token);
		userInfo=userRepo.findUserById(userId);
		if(userInfo != null)
		{
			LabelInformation labelInfo=labelJpaRepo.findByName(labelName,userInfo.getUserid());
			if(labelInfo !=null)
			{
				List<NoteInformation> noteList=labelInfo.getNotelist();
				return noteList;
			}
			else
				throw new LabelNotFoundException("Label Not Found...",HttpStatus.NOT_FOUND);
		}
		else
			throw new UserNotFoundException("User not found..!",HttpStatus.NOT_FOUND);
	}


	/**
	 * Method is used to retrieve all Label with respect to UserID
	 */
	@Override
	public List<LabelInformation> getLabelsWrtUser(String token)
	{
		long userId=jwtGenerator.parseToken(token);
		userInfo=userRepo.findUserById(userId);
		if(userInfo != null)
		{
			List<LabelInformation> labelList=labelJpaRepo.findLabelsByUserId(userId);
			return labelList;
		}
		else
			throw new UserNotFoundException("User not found..!",HttpStatus.NOT_FOUND);
	}
}
