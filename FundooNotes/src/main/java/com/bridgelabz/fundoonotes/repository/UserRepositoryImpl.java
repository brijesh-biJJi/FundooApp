package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.entity.User;

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
	public User save(User info) {
		Session ses=entityManager.unwrap(Session.class);
		ses.saveOrUpdate(info);
		return info;
	}

	/**
	 * This method is used to retrieve the user details from the Database based on specified user email
	 */
	@Override
	public User getUser(String email) {
		Session session = entityManager.unwrap(Session.class);
		Query q = session.createQuery("FROM UserInformation where email =:email");
		q.setParameter("email", email);
		return (User) q.uniqueResult();
	}

	/**
	 * This method is used to Update the particular record from the Database
	 */
	@Override
	public boolean updateUserInfoIsVerifiedCol(Long userId) {
		Session session = entityManager.unwrap(Session.class);
		TypedQuery<User> q = session.createQuery("update UserInformation set is_verified =:true where userid =:userid");
		q.setParameter("true", true);
		q.setParameter("userid", userId);
		try {
			q.executeUpdate();
			return true;
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			
		}
		return false;
	}

	/**
	 * This method is used to retrieve user from the Database by UserId
	 */
	@Override
	public User findUserById(Long id) {
		Session session = entityManager.unwrap(Session.class);
		Query q = session.createQuery("FROM UserInformation where id=:id");
		q.setParameter("id", id);
		return (User) q.uniqueResult();
	}

	/**
	 * This method is used to retrieve all the users from the Database
	 */
	@Override
	public List<User> getUsers() {
		Session session = entityManager.unwrap(Session.class);
		List usersList = session.createQuery("from UserInformation").getResultList();
		return usersList;
	}

	/**
	 * This method is used to update the password of specified user
	 */
	@Override
	public boolean updatePass(UpdatePassword passwordUpdateInfo, Long id) {
		Session session = entityManager.unwrap(Session.class);
		Query q = session.createQuery("update UserInformation set password =:pass" + " " + " " + "where id=:i");
		q.setParameter("pass", passwordUpdateInfo.getConfirmPassword());
		q.setParameter("i", id);
		int status = q.executeUpdate();
		if (status > 0) {
			return true;

		} else {
			return false;
		}
	}
}
