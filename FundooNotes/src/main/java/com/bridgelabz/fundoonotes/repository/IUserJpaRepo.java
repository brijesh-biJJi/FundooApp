package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.entity.User;

@Repository
public interface IUserJpaRepo extends JpaRepository<User, Long> 
{
//
//	@Query("from collaborator_note where note_id=:noteId")
//	public List<User> findCollaboratorsByNoteId(long noteId);
}
