package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.dto.PasswordUpdate;
import com.bridgelabz.fundoonotes.entity.UserInformation;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public UserInformation save(UserInformation info) {
		Session ses=entityManager.unwrap(Session.class);
		ses.saveOrUpdate(info);
		return info;
	}

	@Override
	public UserInformation getUser(String email) {
		Session session = entityManager.unwrap(Session.class);
		Query q = session.createQuery("FROM UserInformation where email =:email");
		q.setParameter("email", email);
		return (UserInformation) q.uniqueResult();
	}

	@Override
	public UserInformation getUserById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updatePassword(PasswordUpdate info, Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean verify(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<UserInformation> getUsers() {
		Session ses = entityManager.unwrap(Session.class);
		List usersList = ses.createQuery("from UserInformation").getResultList();
		return usersList;
	}

}
