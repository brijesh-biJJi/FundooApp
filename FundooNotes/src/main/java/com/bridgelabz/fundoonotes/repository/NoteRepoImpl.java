package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
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

	@Override
	public NoteInformation findNoteById(Long noteid) {
		Session session = entityManager.unwrap(Session.class);
		Query qry = session.createQuery("from NoteInformation where id=:id");
		qry.setParameter("id", noteid);
		return (NoteInformation) qry.uniqueResult();
	}

	@Override
	public boolean deleteNotePermanently(Long noteid) {
		Session session=entityManager.unwrap(Session.class);
		Query qry=session.createQuery("delete from NoteInformation where id=:id");
		qry.setParameter("id", noteid);
		int result = qry.executeUpdate();
		if (result >= 1) {
			return true;

		}
		return false;
	}

	@Override
	public List<NoteInformation> getAllNotes(long userId) 
	{
		Session session=entityManager.unwrap(Session.class);
		return session.createQuery("from NoteInformation where user_id='" + userId + "'" + " and is_trashed=false and is_archieved=false ORDER BY id DESC")
				.getResultList();
		
	}

}
