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
	//private int statuscode;
	private Object obj;
//	public Response(String token, int statuscode, Object obj) {
//		super();
//		this.token = token;
//		this.statuscode = statuscode;
//		this.obj = obj;
//	}
	public Response(String msg, Object obj) {
		super();
		this.message = msg;
		this.obj = obj;
	}
}
