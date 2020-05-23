package com.bridgelabz.fundoonotes.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
@Data
public class UpdateNotes {

	@NotBlank
	private Long noteid;
	
	@NotNull
	private String title;
	
	@NotNull
	private String description;
	
	private boolean isArchieved;
	
	private boolean isPinned;
	
	private boolean isTrashed;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;

	public Long getNoteid() {
		return noteid;
	}

	public void setNoteid(Long noteid) {
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
	
	
}
