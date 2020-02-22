package com.bridgelabz.fundoonotes.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

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
}
