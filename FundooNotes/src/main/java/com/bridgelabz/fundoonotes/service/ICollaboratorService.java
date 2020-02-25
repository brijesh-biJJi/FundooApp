package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.CollaboratorDto;
import com.bridgelabz.fundoonotes.entity.CollaboratorInformation;

public interface ICollaboratorService {

	CollaboratorInformation createCollab(String token, CollaboratorDto collabDtoInfo, long noteId);

	
}
