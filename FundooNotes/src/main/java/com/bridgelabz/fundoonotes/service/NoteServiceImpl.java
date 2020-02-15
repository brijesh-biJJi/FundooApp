package com.bridgelabz.fundoonotes.service;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.repository.INoteRepo;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.utility.JWTGenerator;

@Service
public class NoteServiceImpl implements INoteService {

	@Autowired
	private INoteRepo noteRepo;
	private NoteInformation noteInfo=new NoteInformation();
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private JWTGenerator jwtGenerate;
	@Autowired
	private BCryptPasswordEncoder encrypt;
	@Autowired
	private ModelMapper modelMapper;
	@Override
	
	
	public NoteInformation createNote(NoteDto noteDtoInfo, String token)
	{
		Long userId = (Long) jwtGenerate.parseToken(token);
		UserInformation userInfo = userRepo.fingUserById(userId);
		if (userInfo != null)
		{
			noteInfo = modelMapper.map(noteDtoInfo, NoteInformation.class);
			noteInfo.setCreatedAt(LocalDateTime.now());
			noteInfo.setArchieved(false);
			noteInfo.setPinned(false);
			noteInfo.setTrashed(false);
			noteInfo.setColour("white");
			//userInfo.getNote().add(noteInfo);
			NoteInformation note = noteRepo.saveNote(noteInfo);
			if (note != null) 
			{
				final String key = userInfo.getEmail();
			}
		}
		return noteInfo;
	}

}
