package com.bridgelabz.fundoonotes.service;

import java.util.Optional;

import com.bridgelabz.fundoonotes.dto.EditLabel;
import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.entity.LabelInformation;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
public interface ILabelService {

	LabelInformation createLabel(String token, LabelDto labelDtoInfo);

	LabelInformation addMapLabel(String token, LabelDto labelDtoInfo, long noteId);

	LabelInformation removeNoteLabel(String token, LabelDto labelDtoInfo, long noteId);

	LabelInformation deleteUserLabel(String token, LabelDto labelDtoInfo, long noteId);

	Optional<LabelInformation> editLabel(String token, EditLabel editlabelInfo);

}
