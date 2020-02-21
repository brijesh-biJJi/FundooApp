package com.bridgelabz.fundoonotes.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.entity.LabelInformation;

@Repository
public interface ILabelJpaRepo extends JpaRepository<LabelInformation, Long>
{
	@Query("from LabelInformation where label_name=:name and user_id=:id")
	public LabelInformation findByName(String name,long id);
	
	@Transactional
	@Modifying
	@Query("delete from LabelInformation where label_name=:name")
	void deleteByName(String name);
}