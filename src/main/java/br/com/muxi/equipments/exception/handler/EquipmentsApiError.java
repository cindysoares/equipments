package br.com.muxi.equipments.exception.handler;

import br.com.muxi.equipments.exception.EquipmentsApiException;

public class EquipmentsApiError {	
	
	private String exception;
	private String message;
	
	public EquipmentsApiError(EquipmentsApiException ex) {
		this.message = ex.getMessage();
		this.exception = ex.getClass().getCanonicalName();
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getException() {
		return exception;
	}
	
}
