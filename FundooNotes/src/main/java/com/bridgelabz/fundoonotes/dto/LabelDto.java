package com.bridgelabz.fundoonotes.dto;

import lombok.Data;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
@Data
public class LabelDto {
	private String labelName;

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	
	
}
