package com.bridgelabz.fundoonotes.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="collaboratorinfo")
public class CollaboratorInformation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long collaboratorId;
	
	private String email;
	

	@ManyToOne
	@JoinColumn(name="noteId")
	private NoteInformation noteCollaborator;
}
