package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.entity.LabelInformation;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
public interface ILabelService {

	LabelInformation createLabel(String token, LabelDto labelDtoInfo);

}
