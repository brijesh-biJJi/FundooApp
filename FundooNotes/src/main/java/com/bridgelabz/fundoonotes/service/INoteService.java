package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.UpdateNotes;
import com.bridgelabz.fundoonotes.entity.NoteInformation;

public interface INoteService {
	NoteInformation createNote(NoteDto information, String token);

	void updateNote(UpdateNotes updateNoteinfo, String token);
}
