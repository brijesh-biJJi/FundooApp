package com.bridgelabz.fundoonotes.response;

import lombok.Data;

/**
 * 
 * @author Brijesh A Kanchan
 *
 */
@Data
public class Response {

	private String message;
	private String token;
	private Object obj;
	public Response(String msg,String token) {
		super();
		this.message = msg;
		this.token = token;
	}
	public Response(String msg, Object obj) {
		super();
		this.message = msg;
		this.obj = obj;
	}
}
