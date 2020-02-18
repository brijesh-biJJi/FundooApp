package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import com.bridgelabz.fundoonotes.entity.NoteInformation;

public interface INoteRepo {
	NoteInformation saveNote(NoteInformation noteInfo);

	NoteInformation findNoteById(Long noteid);

	boolean deleteNotePermanently(Long noteid);

	List<NoteInformation> getAllNotes(long userId);
}
