package com.bridgelabz.fundoonotes.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.entity.NoteInformation;

@Repository
public class NoteRepoImpl implements INoteRepo {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public NoteInformation saveNote(NoteInformation noteInfo) 
	{
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(noteInfo);
		return noteInfo;
	}

}
