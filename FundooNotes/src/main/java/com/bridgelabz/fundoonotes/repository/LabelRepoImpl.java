package com.bridgelabz.fundoonotes.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.entity.LabelInformation;

@Repository
public class LabelRepoImpl implements ILabelRepo{

	@PersistenceContext
	private EntityManager entityManager;
	@Override
	public LabelInformation checkLabel(long userId, String name) 
	{
		System.out.println("repo1");
		Session session=entityManager.unwrap(Session.class);
		Query qry=session.createQuery("from LabelInformation where label_name=:name and user_id=:id ");
		qry.setParameter("name", name);
		qry.setParameter("id", userId);
		System.out.println("repo2");
		return (LabelInformation) qry.uniqueResult();
	}
	@Override
	public LabelInformation save(LabelInformation labelInfo) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(labelInfo);
		return labelInfo;
	}

}
