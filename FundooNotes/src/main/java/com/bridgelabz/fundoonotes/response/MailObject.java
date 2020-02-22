package com.bridgelabz.fundoonotes.response;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
@Data
@Component
public class MailObject implements Serializable {

	private String email;
	private String subject;
	private  String message;
}
