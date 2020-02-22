package com.bridgelabz.fundoonotes.service;

import java.util.List;
import java.util.Optional;

import com.bridgelabz.fundoonotes.dto.EditLabel;
import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.entity.LabelInformation;
import com.bridgelabz.fundoonotes.entity.NoteInformation;

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

	LabelInformation editLabel(String token, EditLabel editlabelInfo);

	List<NoteInformation> retrieveNotes(String token, String labelName);

	List<LabelInformation> getLabelsWrtUser(String token);

}
