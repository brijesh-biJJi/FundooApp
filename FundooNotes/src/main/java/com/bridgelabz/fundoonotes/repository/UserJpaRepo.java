package com.bridgelabz.fundoonotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.entity.Profile;

/**
 * 
 * @author Brijesh A kanchan
 *
 */
@Repository
public interface UserJpaRepo extends JpaRepository<Profile, Long> {

	@Modifying
	@Query("delete from Profile where user_id=:id")
	void deleteByUserId(long id);
	
	@Query("from Profile where user_id=:id")
	Profile findByUserId(long id);
	
	@Modifying
	@Query("update from Profile set profile_pic_name=:name where user_id=:id")
	int updateByUserId(String name,long id);
}
