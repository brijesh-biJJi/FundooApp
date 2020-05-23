package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.ReminderDto;
import com.bridgelabz.fundoonotes.dto.UpdateNotes;
import com.bridgelabz.fundoonotes.entity.LabelInformation;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
/**
 * 
 * @author Brijesh A Kanchan
 *
 */
public interface INoteService {
	NoteInformation createNote(NoteDto information, String token);

	void updateNote(UpdateNotes updateNoteinfo, String token);

	boolean pinNote(Long id, String token);

	boolean archiveNote(Long id, String token);

	boolean moveToTrash(Long id, String token);

	boolean deleteNotePermanently(Long id, String token);

	boolean changeColor(Long id, String color, String token);

	List<NoteInformation> getOtherNotes(String token);

	List<NoteInformation> getTrashedNotes(String token);

	List<NoteInformation> getArchivedNotes(String token);

	List<NoteInformation> getPinnedNotes(String token);

	boolean addReminder(String token, long noteId,ReminderDto reminderDtoInfo);

	boolean removeReminder(String token, long noteId);

	List<LabelInformation> retrieveLabels(String token, long noteId);

	List<NoteInformation> searchByTitle(String title);

	/**
	 * Method is used to return list of Notes
	 */
	List<NoteInformation> getAllNotes(String token);
}
