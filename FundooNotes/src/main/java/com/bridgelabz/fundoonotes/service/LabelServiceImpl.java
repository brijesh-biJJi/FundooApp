package com.bridgelabz.fundoonotes.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.entity.LabelInformation;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.exceptions.NoteIdNotFoundException;
import com.bridgelabz.fundoonotes.exceptions.UserNotFoundException;
import com.bridgelabz.fundoonotes.repository.ILabelJpaRepo;
import com.bridgelabz.fundoonotes.repository.ILabelRepo;
import com.bridgelabz.fundoonotes.repository.INoteRepo;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.utility.JWTGenerator;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
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
	
	private UserInformation userInfo=new UserInformation();
	
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
			System.out.println("1st");
			//LabelInformation labelInfo=labelRepo.checkLabel(userId,labelDtoInfo.getName());
			//System.out.println("Label :"+labelInfo);
			LabelInformation labelInfo=labelJpaRepo.findByName(labelDtoInfo.getName(),userInfo.getUserid());
			//System.out.println("Label :"+labelInfo.getLabelName());
			if(labelInfo == null)
			{
				System.out.println("2nd");
				labelInfo=modelMapper.map(labelDtoInfo, LabelInformation.class);
				labelInfo.getLabelId();
				labelInfo.getLabelName();
				labelInfo.setUserId(userInfo.getUserid());
				//LabelInformation label=labelRepo.save(labelInfo);
				labelJpaRepo.save(labelInfo);
				return labelInfo;
			}
			
		}
		else
			throw new UserNotFoundException("User not found..!");
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
				LabelInformation labelInfo=labelJpaRepo.findByName(labelDtoInfo.getName(),userInfo.getUserid());
				System.out.println("check1"+labelInfo);
				if(labelInfo!=null)
				{
//					noteInfo.getLabelList().add(labelInfo);
//					noteRepo.saveNote(noteInfo);
					
					labelInfo.getNotelist().add(noteInfo);
					labelJpaRepo.save(labelInfo);
					return labelInfo;
				}
				else
				{
					labelInfo=modelMapper.map(labelDtoInfo, LabelInformation.class);
					labelInfo.getLabelId();
					labelInfo.getLabelName();
					labelInfo.setUserId(userInfo.getUserid());
					labelJpaRepo.save(labelInfo);
					
					noteInfo.getLabelList().add(labelInfo);
					noteRepo.saveNote(noteInfo);
					labelJpaRepo.save(labelInfo);
					System.out.println("AFter save ");
					return labelInfo;
				}
			}
			else
				throw new NoteIdNotFoundException("Note Id is not found");
		}
		else
			throw new UserNotFoundException("User not found..!");
		
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
				LabelInformation labelInfo=labelJpaRepo.findByName(labelDtoInfo.getName(),userInfo.getUserid());
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
				throw new NoteIdNotFoundException("Note Id is not found");
		}
		else
			throw new UserNotFoundException("User not found..!");
		
	}
	
	

}
