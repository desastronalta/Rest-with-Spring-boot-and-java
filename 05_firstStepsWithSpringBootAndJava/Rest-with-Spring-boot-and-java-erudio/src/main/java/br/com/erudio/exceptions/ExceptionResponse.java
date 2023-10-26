package br.com.erudio.exceptions;

import java.io.Serializable;
import java.util.Date;

public class ExceptionResponse implements Serializable {
/*
 * o serializable é utilizado para fazer instancias em grandes aplicações sendo algo
 * ulilizado em qualquer framework devido aos grandes sistemas que se utilizam dela.
 */
	private static final long serialVersionUID = 1L; 
	
	private Date timestamp;
	//para mostrar a hora que ocorreu o erro;
	private String message;
	// mostrar a mensagem de erro;
	private String details;
	// mostrar os detalhes;
	 
	public ExceptionResponse(Date timestamp, String message, String details) {
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

}
