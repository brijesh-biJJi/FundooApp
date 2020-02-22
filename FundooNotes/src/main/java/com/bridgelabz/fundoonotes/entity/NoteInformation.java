package com.bridgelabz.fundoonotes.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
@Data
@Entity
@Table(name="noteinfo")
public class NoteInformation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long noteid;

	private String title;

	private String description;

	@Column(columnDefinition = "boolean default false", nullable=false)
	private boolean isArchieved;

	@Column(columnDefinition = "boolean default false", nullable=false)
	private boolean isPinned;

	@Column(columnDefinition = "boolean default false", nullable=false)
	private boolean isTrashed;

	@JsonIgnore
	private LocalDateTime createdAt;

	@JsonIgnore
	private LocalDateTime updatedAt;

	private String colour;

	private LocalDateTime reminder;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Label_Note", joinColumns = { @JoinColumn(name = "note_id") }, 
									inverseJoinColumns = { @JoinColumn(name = "label_id") })
	private List<LabelInformation> labelList;

}
