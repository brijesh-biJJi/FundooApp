package com.bridgelabz.fundoonotes.repository;

import com.bridgelabz.fundoonotes.entity.NoteInformation;

public interface INoteRepo {
	NoteInformation saveNote(NoteInformation noteInfo);
}
