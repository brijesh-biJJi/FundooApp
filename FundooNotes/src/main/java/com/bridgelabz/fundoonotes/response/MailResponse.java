package com.bridgelabz.fundoonotes.response;

import org.springframework.stereotype.Component;

@Component
public class MailResponse {
	public String fromMsg(String url, String token) {
		return url + "/" + token;
	}
}
