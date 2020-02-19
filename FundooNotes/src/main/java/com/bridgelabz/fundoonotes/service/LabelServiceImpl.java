package com.bridgelabz.fundoonotes.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.entity.LabelInformation;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.exceptions.UserNotFoundException;
import com.bridgelabz.fundoonotes.repository.ILabelRepo;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.utility.JWTGenerator;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
@Service
public class LabelServiceImpl implements ILabelService {

	@Autowired
	private ILabelRepo labelRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private JWTGenerator jwtGenerator;
	
	@Autowired
	private UserRepository userRepo;
	
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
			LabelInformation labelInfo=labelRepo.checkLabel(userId,labelDtoInfo.getName());
			if(labelInfo == null)
			{
				labelInfo=modelMapper.map(labelDtoInfo, LabelInformation.class);
				labelInfo.getLabelId();
				labelInfo.getLabelName();
				labelInfo.setUserId(userInfo.getUserid());
				LabelInformation label=labelRepo.save(labelInfo);
			}
			return labelInfo;
		}
		else
			throw new UserNotFoundException("User not found..!");
	}

}
