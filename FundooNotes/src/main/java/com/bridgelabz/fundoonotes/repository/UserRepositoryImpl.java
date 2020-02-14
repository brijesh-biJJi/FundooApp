package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
	
	/**
	 * This method is used to save the UserDetails into the Database
	 */
	@Override
	public UserInformation save(UserInformation info) {
		Session ses=entityManager.unwrap(Session.class);
		ses.saveOrUpdate(info);
		return info;
	}

	/**
	 * This method is used to retrieve the user details from the Database based on specified user email
	 */
	@Override
	public UserInformation getUser(String email) {
		Session session = entityManager.unwrap(Session.class);
		Query q = session.createQuery("FROM UserInformation where email =:email");
		q.setParameter("email", email);
		return (UserInformation) q.uniqueResult();
	}

	/**
	 * This method is used to Update the particular record from the Database
	 */
	@Override
	public boolean updateUserInfoIsVerifiedCol(Long userId) {
		Session session = entityManager.unwrap(Session.class);
		TypedQuery<UserInformation> q = session.createQuery("update UserInformation set is_verified =:true where userid =:userid");
		q.setParameter("true", true);
		q.setParameter("userid", userId);
		try {
			q.executeUpdate();
			return true;
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			return false;
		}
	}
}
