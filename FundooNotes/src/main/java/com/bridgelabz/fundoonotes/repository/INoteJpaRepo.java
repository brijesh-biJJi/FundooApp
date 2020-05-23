package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.entity.Profile;

public interface INoteJpaRepo extends JpaRepository<NoteInformation, Long> {
	@Query("from NoteInformation where user_id=:id and is_trashed=false and is_archieved=false and is_pinned=false")
	List<NoteInformation> findByUserId(long id);
	
	@Query("from NoteInformation where user_id=:id")
	List<NoteInformation> findNoteByUserId(long id);
}
