package com.bridgelabz.fundoonotes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.entity.LabelInformation;
import com.bridgelabz.fundoonotes.repository.ILabelRepo;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
@Service
public class LabelServiceImpl implements ILabelService {

	@Autowired
	ILabelRepo labelRepo;
	
	/**
	 * Method is used to create Label
	 */
	@Override
	public LabelInformation createLabel(String token, LabelDto labelDtoInfo)
	{
		
		return null;
	}

}
