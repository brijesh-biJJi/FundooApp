package com.bridgelabz.fundoonotes.repository;

import com.bridgelabz.fundoonotes.entity.LabelInformation;
/**
 * 
 * @author Brijesh A Kanchan
 *
 */
public interface ILabelRepo {

	LabelInformation checkLabel(long userId, String name);

	LabelInformation save(LabelInformation labelInfo);

}
