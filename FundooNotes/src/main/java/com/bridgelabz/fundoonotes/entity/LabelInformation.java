package com.bridgelabz.fundoonotes.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
@Data
@Entity
@Table(name="labelinfo")
public class LabelInformation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long labelId;
	private String labelName;
	private long userId;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Label_Note", joinColumns = { @JoinColumn(name = "label_id") }, inverseJoinColumns = {
			@JoinColumn(name = "note_id") })
	@JsonBackReference
	private List<NoteInformation> notelist;

}
