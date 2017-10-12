package br.com.muxi.equipments.exception;

@SuppressWarnings("serial")
public class EquipmentsApiException extends Exception {
	
	public EquipmentsApiException() {
		super();
	}
	
	public EquipmentsApiException(String message) {
		super(message);
	}

}
