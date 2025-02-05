package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.entity.NoteInformation;

public interface IElasticSearchService {

	String createNote(NoteInformation note);

	List<NoteInformation> searchByTitle(String title);

	String deleteNote(NoteInformation noteInfo);

}
