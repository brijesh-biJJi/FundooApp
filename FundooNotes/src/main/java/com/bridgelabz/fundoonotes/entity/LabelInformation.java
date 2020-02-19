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

@Entity
@Table(name="lableinfo")
public class LabelInformation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int lableId;
	private String lableName;
	private long userId;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Label_Note", joinColumns = { @JoinColumn(name = "label_id") }, inverseJoinColumns = {
			@JoinColumn(name = "note_id") })
	@JsonBackReference
	private List<NoteInformation> notelist;

	public int getLableId() {
		return lableId;
	}

	public void setLableId(int lableId) {
		this.lableId = lableId;
	}

	public String getLableName() {
		return lableName;
	}

	public void setLableName(String lableName) {
		this.lableName = lableName;
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
