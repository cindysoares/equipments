package br.com.muxi.equipments.exception.handler;

public class EquipmentsApiError {	
	
	protected String exception;
	protected String message;
	
	public EquipmentsApiError(Exception ex) {
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
