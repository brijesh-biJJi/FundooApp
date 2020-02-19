package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.entity.LabelInformation;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.ILabelService;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
@RestController
public class Labelcontroller {
	
	@Autowired
	ILabelService labelService;
	
	/**
	 * API for creating Label
	 * @param token
	 * @param labelDtoInfo
	 * @return
	 */
	@PostMapping("label/createLable")
	public ResponseEntity<Response> createLabel(@RequestHeader("token") String token,@RequestBody LabelDto labelDtoInfo)
	{
		LabelInformation labelInfo=labelService.createLabel(token,labelDtoInfo);
		if(labelInfo != null)
			return ResponseEntity.status(HttpStatus.CREATED).body(new Response("Label is created successfully", 200, labelInfo));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Something went wrong", 400,labelInfo));
	
	}
}
