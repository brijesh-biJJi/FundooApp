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

	public long getLabelId() {
		return labelId;
	}

	public void setLabelId(long labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public List<NoteInformation> getNotelist() {
		return notelist;
	}

	public void setNotelist(List<NoteInformation> notelist) {
		this.notelist = notelist;
	}
	
	

}
