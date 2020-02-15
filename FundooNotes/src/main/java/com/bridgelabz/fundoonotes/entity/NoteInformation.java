package com.bridgelabz.fundoonotes.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "noteinfo")
public class NoteInformation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long noteId;

	private String title;

	private String desc;

	@Column(columnDefinition = "boolean default false")
	private boolean isArchieved;

	@Column(columnDefinition = "boolean default false")
	private boolean isPinned;

	@Column(columnDefinition = "boolean default false")
	private boolean isTrashed;

	@JsonIgnore
	private LocalDateTime createdAt;

	@JsonIgnore
	private LocalDateTime updatedAt;

	private String colour;

	private LocalDateTime reminder;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "userId")
	private UserInformation userNotes;
}
