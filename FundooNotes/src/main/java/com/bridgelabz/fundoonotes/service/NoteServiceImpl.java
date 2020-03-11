package com.bridgelabz.fundoonotes.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.ReminderDto;
import com.bridgelabz.fundoonotes.dto.UpdateNotes;
import com.bridgelabz.fundoonotes.entity.LabelInformation;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.User;
import com.bridgelabz.fundoonotes.exceptions.LabelNotFoundException;
import com.bridgelabz.fundoonotes.exceptions.NoteIdNotFoundException;
import com.bridgelabz.fundoonotes.exceptions.UserNotFoundException;
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
	
	@Autowired
	private IElasticSearchService elasticSearchService;
	
	User userInfo=new User();
	
	/**
	 * Method is used to create a Note
	 */
	@Transactional
	@Override
	public NoteInformation createNote(NoteDto noteDtoInfo, String token)
	{
		Long userId = (Long) jwtGenerate.parseToken(token);
		userInfo = userRepo.findUserById(userId);
		if (userInfo != null)
		{
			noteInfo = modelMapper.map(noteDtoInfo, NoteInformation.class);
			userInfo.getNoteList().add(noteInfo);
			noteInfo.setCreatedAt(LocalDateTime.now());
			noteInfo.setArchieved(false);
			noteInfo.setPinned(false);
			noteInfo.setTrashed(false);
			noteInfo.setColour("white");
			NoteInformation note = noteRepo.saveNote(noteInfo);
			
			try {
				elasticSearchService.createNote(noteInfo);
			}
			catch(Exception e)
			{
				log.info(e.getMessage());
			}
			return noteInfo;
		}
		else
			throw new UserNotFoundException("User not found..!");	
	}

	/**
	 * Method is used to update a Note
	 */
	@Transactional
	@Override
	public void updateNote(UpdateNotes updateNoteinfo, String token) 
	{
		try 
		{
			Long userid = (Long) jwtGenerate.parseToken(token);
			userInfo = userRepo.findUserById(userid);
			if(userInfo != null)
			{
				NoteInformation noteInfo = noteRepo.findNoteById(updateNoteinfo.getNoteid());
				if (noteInfo != null) 
				{
					noteInfo.setNoteid(updateNoteinfo.getNoteid());
					noteInfo.setDescription(updateNoteinfo.getDescription());
					noteInfo.setTitle(updateNoteinfo.getTitle());
					noteInfo.setPinned(updateNoteinfo.isPinned());
					noteInfo.setTrashed(updateNoteinfo.isTrashed());
					noteInfo.setArchieved(updateNoteinfo.isArchieved());
					noteInfo.setUpdatedAt(updateNoteinfo.getUpdatedAt());
					noteRepo.saveNote(noteInfo);
				}
				else
					throw new NoteIdNotFoundException("Note Id is not found");
			}
			else
				throw new UserNotFoundException("User not found..!");	
		} 
		catch (Exception e) {
			log.info(e.getMessage());
		}
	}

	
	/**
	 * Method is used to Pin a Note
	 * @return 
	 */
	@Transactional
	@Override
	public boolean pinNote(Long id, String token) {
		try {

			Long userid = (Long) jwtGenerate.parseToken(token);

			userInfo = userRepo.findUserById(userid);
			if(userInfo != null)
			{
				NoteInformation noteInfo = noteRepo.findNoteById(id);
				if (noteInfo != null) {
					noteInfo.setArchieved(false);
					noteInfo.setPinned(!noteInfo.isPinned());
					noteRepo.saveNote(noteInfo);
					return true;
				}
				else
					throw new NoteIdNotFoundException("Note Id is not found");
			}
			else
				throw new UserNotFoundException("User not found..!");	
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return false;
	}

	
	/**
	 * Method is used to Archive a Note
	 * @return 
	 */
	@Transactional
	@Override
	public boolean archiveNote(Long id, String token) {
		try
		{
			Long userId=jwtGenerate.parseToken(token);
			userInfo = userRepo.findUserById(userId);
			if(userInfo != null)
			{
				NoteInformation noteInfo=noteRepo.findNoteById(id);
				if(noteInfo !=null)
				{
					noteInfo.setPinned(false);
					noteInfo.setArchieved(!noteInfo.isArchieved());
					noteRepo.saveNote(noteInfo);
					return true;
				}
				else
					throw new NoteIdNotFoundException("Note Id is not found");
			}
			else
				throw new UserNotFoundException("User not found..!");			
		}
		catch(Exception e)
		{
			log.info(e.getMessage());
		}
		return false;
	}

	/**
	 * Method is used to move a Note to Trash
	 * @return 
	 */
	@Transactional
	@Override
	public boolean moveToTrash(Long id, String token) 
	{
		try
		{
			long userId=jwtGenerate.parseToken(token);
			userInfo = userRepo.findUserById(userId);
			if(userInfo != null)
			{
				NoteInformation nnoteId,color,oteInfo=noteRepo.findNoteById(id);
				if(noteInfo != null)
				{
					noteInfo.setTrashed(!noteInfo.isTrashed());
					noteRepo.saveNote(noteInfo);
					return true;
				}
				else
					throw new NoteIdNotFoundException("Note Id is not found");
			}
			else
				throw new UserNotFoundException("User not found..!");	
		}
		catch(Exception e) {
			log.info(e.getMessage());
		}
		return false;
	}

	/**
	 * Method is used to delete the Note Permamnently
	 * @return 
	 */
	@Transactional
	@Override
	public boolean deleteNotePermanently(Long id, String token) {
		try
		{
			long userId=jwtGenerate.parseToken(token);
			NoteInformation noteInfo=noteRepo.findNoteById(id);
			if(noteInfo != null)
			{
				noteRepo.deleteNotePermanently(id);
				elasticSearchService.deleteNote(noteInfo);
				return true;
			}
		}catch(Exception e) {
			log.info(e.getMessage());
		}
		return false;
	}

	/**
	 * Method is used to change the color ofNote
	 * @return 
	 */
	@Transactional
	@Override
	public boolean changeColor(Long noteId, String color, String token) 
	{
		long userId=jwtGenerate.parseToken(token);
		userInfo=userRepo.findUserById(userId);
		if(userInfo != null)
		{
			NoteInformation noteInfo=noteRepo.findNoteById(noteId);
			if(noteInfo != null)
			{
				noteInfo.setColour(color);
				noteRepo.saveNote(noteInfo);
				return true;
			}
			else
				throw new NoteIdNotFoundException("Note Id is not found");
		}
		else 
			throw new UserNotFoundException("User not found..!");
	}

	/**
	 * Method is used to return list of Notes
	 */
	@Override
	public List<NoteInformation> getAllNotes(String token) 
	{
		long userId=jwtGenerate.parseToken(token);
		userInfo=userRepo.findUserById(userId);
		if(userInfo != null)
		{
			
			List<NoteInformation> noteList=noteRepo.getAllNotes(userId);
			return noteList;
		}
		else
			throw new UserNotFoundException("User not found..!");
	}

	/**
	 * Method is used to return list of Trashed Notes
	 */
	@Override
	public List<NoteInformation> getTrashedNotes(String token)
	{
		long userId=jwtGenerate.parseToken(token);
		userInfo=userRepo.findUserById(userId);
		if(userInfo != null)
		{
			List<NoteInformation> noteList=noteRepo.getTrashedNotes(userId);
			return noteList;
		}
		else
			throw new UserNotFoundException("User not found..!");
	}

	/**
	 * Method is used to return the list of Archived Notes
	 */
	@Override
	public List<NoteInformation> getArchivedNotes(String token)
	{
		long userId=jwtGenerate.parseToken(token);
		userInfo=userRepo.findUserById(userId);
		if(userInfo != null)
		{
			List<NoteInformation> noteList=noteRepo.getArchivedNotes(userId);
			return noteList;
		}
		else
			throw new UserNotFoundException("User not found..!");
	}

	/**
	 * Method is used to return the list of Pinned Notes
	 */
	@Override
	public List<NoteInformation> getPinnedNotes(String token) {
		long userId=jwtGenerate.parseToken(token);
		userInfo=userRepo.findUserById(userId);
		if(userInfo != null)
		{
			List<NoteInformation> noteList=noteRepo.getPinnedNotes(userId);
			return noteList;
		}
		else
			throw new UserNotFoundException("User not found..!");
	}

	/**
	 * Method is used to add reminder to specified  Note
	 */
	@Transactional
	@Override
	public boolean addReminder(String token,long noteId, ReminderDto reminderDtoInfo) 
	{
		long userId=jwtGenerate.parseToken(token);
		userInfo=userRepo.findUserById(userId);
		
		if(userInfo != null)
		{
			NoteInformation noteInfo=noteRepo.findNoteById(noteId);
			if(noteInfo != null)
			{
				noteInfo.setReminder(reminderDtoInfo.getReminder());
				noteRepo.saveNote(noteInfo);
				return true;
			}
			else
				throw new NoteIdNotFoundException("Note Id Not Found..");
		}
		else
			throw new UserNotFoundException("User Not Found..!");
	}

	/**
	 * Method is used to remove reminder of specified  Note
	 */
	@Transactional
	@Override
	public boolean removeReminder(String token, long noteId, ReminderDto reminderDtoInfo) 
	{
		long userId=jwtGenerate.parseToken(token);
		userInfo=userRepo.findUserById(userId);
		if(userInfo !=null)
		{
			NoteInformation noteInfo=noteRepo.findNoteById(noteId);
			if(noteInfo !=null)
			{
				noteInfo.setReminder(null);
				noteRepo.saveNote(noteInfo);
				return true;
			}
			else
				throw new NoteIdNotFoundException("Not not found..!");
		}
		else
			throw new UserNotFoundException("User Not Found....!");
	}

	/**
	 * Method is used to retrieve all Labels Associated with particular Note
	 */
	@Override
	public List<LabelInformation> retrieveLabels(String token, long noteId) {
		long userId=jwtGenerate.parseToken(token);
		userInfo=userRepo.findUserById(userId);
		if(userInfo != null)
		{
			NoteInformation noteInfo=noteRepo.findNoteById(noteId);
			if(noteInfo !=null)
			{
				List<LabelInformation> listLabels=noteInfo.getLabelList();
				return listLabels;
			}
			else
				throw new NoteIdNotFoundException("Not not found..!");
		}
		else
			throw new UserNotFoundException("User not found..!");
	}

	/**
	 * Method is used to Search the Note by title
	 */
	@Override
	public List<NoteInformation> searchByTitle(String title) {
		List<NoteInformation> notes=elasticSearchService.searchByTitle(title);
		if(notes!=null) {
		return notes;
		}
		else {
			return null;
		}
	}
}
