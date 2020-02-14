package com.bridgelabz.fundoonotes.response;

import org.springframework.stereotype.Component;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
@Component
public class MailResponse 
{
	public String mergeMsg(String url, String token) {
		return url + "/" + token;
	}
}
