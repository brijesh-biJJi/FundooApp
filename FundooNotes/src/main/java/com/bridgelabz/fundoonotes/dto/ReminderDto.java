package com.bridgelabz.fundoonotes.dto;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
@Data
public class ReminderDto {
	private LocalDateTime reminder;

	public LocalDateTime getReminder() {
		return reminder;
	}

	public void setReminder(LocalDateTime reminder) {
		this.reminder = reminder;
	}
	
	
	
}
