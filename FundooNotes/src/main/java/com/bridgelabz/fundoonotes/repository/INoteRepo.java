package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import com.bridgelabz.fundoonotes.entity.NoteInformation;
/**
 * 
 * @author Brijesh A Kanchan
 *
 */
public interface INoteRepo {
	NoteInformation saveNote(NoteInformation noteInfo);

	NoteInformation findNoteById(Long noteid);

	boolean deleteNotePermanently(Long noteid);

	List<NoteInformation> getAllNotes(long userId);

	List<NoteInformation> getTrashedNotes(long userId);

	List<NoteInformation> getArchivedNotes(long userId);

	List<NoteInformation> getPinnedNotes(long userId);
}
