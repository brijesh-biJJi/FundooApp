package com.bridgelabz.fundoonotes.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.UpdateNotes;
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
	
	UserInformation userInfo=new UserInformation();
	
	@Transactional
	@Override
	public NoteInformation createNote(NoteDto noteDtoInfo, String token)
	{
		Long userId = (Long) jwtGenerate.parseToken(token);
		userInfo = userRepo.findUserById(userId);
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

	@Transactional
	@Override
	public void updateNote(UpdateNotes updateNoteinfo, String token) 
	{
		try {
			Long userid = (Long) jwtGenerate.parseToken(token);

			userInfo = userRepo.findUserById(userid);
			NoteInformation noteInfo = noteRepo.findNoteById(updateNoteinfo.getNoteid());
			if (noteInfo != null) {
				noteInfo.setNoteid(updateNoteinfo.getNoteid());
				noteInfo.setDescription(updateNoteinfo.getDescription());
				noteInfo.setTitle(updateNoteinfo.getTitle());
				noteInfo.setPinned(updateNoteinfo.isPinned());
				noteInfo.setTrashed(updateNoteinfo.isTrashed());
				noteInfo.setArchieved(updateNoteinfo.isArchieved());
				noteInfo.setUpdatedAt(updateNoteinfo.getUpdatedAt());
				noteRepo.saveNote(noteInfo);

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Transactional
	@Override
	public void pinNote(Long id, String token) {
		try {

			Long userid = (Long) jwtGenerate.parseToken(token);

			userInfo = userRepo.findUserById(userid);
			NoteInformation noteInfo = noteRepo.findNoteById(id);
			if (noteInfo != null) {
				noteInfo.setArchieved(false);
				noteInfo.setPinned(!noteInfo.isPinned());
				noteRepo.saveNote(noteInfo);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Transactional
	@Override
	public void archiveNote(Long id, String token) {
		try
		{
			Long userId=jwtGenerate.parseToken(token);
			NoteInformation noteInfo=noteRepo.findNoteById(id);
			if(noteInfo !=null)
			{
				noteInfo.setPinned(false);
				noteInfo.setArchieved(!noteInfo.isArchieved());
				noteRepo.saveNote(noteInfo);
			}
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

}
