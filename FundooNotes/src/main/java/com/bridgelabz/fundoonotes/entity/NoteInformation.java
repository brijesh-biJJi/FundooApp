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

	
	
	public List<LabelInformation> getLabelList() {
		return labelList;
	}

	public void setLabelList(List<LabelInformation> labelList) {
		this.labelList = labelList;
	}

	public long getNoteid() {
		return noteid;
	}

	public void setNoteid(long noteid) {
		this.noteid = noteid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isArchieved() {
		return isArchieved;
	}

	public void setArchieved(boolean isArchieved) {
		this.isArchieved = isArchieved;
	}

	public boolean isPinned() {
		return isPinned;
	}

	public void setPinned(boolean isPinned) {
		this.isPinned = isPinned;
	}

	public boolean isTrashed() {
		return isTrashed;
	}

	public void setTrashed(boolean isTrashed) {
		this.isTrashed = isTrashed;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public LocalDateTime getReminder() {
		return reminder;
	}

	public void setReminder(LocalDateTime reminder) {
		this.reminder = reminder;
	}
	
	
}
