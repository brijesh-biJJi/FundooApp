package com.bridgelabz.fundoonotes.response;

import lombok.Data;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
@Data
public class Response {

	private String token;
	private int statuscode;
	private Object obj;
	
	public Response(String token, int statuscode, Object obj) {
		super();
		this.token = token;
		this.statuscode = statuscode;
		this.obj = obj;
	}

	public String getToken() {
		return token;
	}

	

	public int getStatuscode() {
		return statuscode;
	}

	

	public Object getObj() {
		return obj;
	}

	
	
	
	
}
