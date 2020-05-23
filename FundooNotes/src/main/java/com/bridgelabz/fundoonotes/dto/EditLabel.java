package com.bridgelabz.fundoonotes.dto;

import lombok.Data;
/**
 * 
 * @author Brijesh A Kanchan
 *
 */
@Data
public class EditLabel {
	private long labelId;	
	private String labelName;
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
	
	
}
