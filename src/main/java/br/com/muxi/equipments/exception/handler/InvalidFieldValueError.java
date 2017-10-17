package br.com.muxi.equipments.exception.handler;

public class InvalidFieldValueError {
	
	protected String field;
	protected String message;
	
	public InvalidFieldValueError(String field, String message) {
		this.field = field;
		this.message = message;
	}
	
	public String getField() {
		return field;
	}
	
	public String getMessage() {
		return message;
	}

}
