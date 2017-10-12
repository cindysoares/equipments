package br.com.muxi.equipments.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.muxi.equipments.exception.EquipmentsApiException;

@ControllerAdvice
public class EquipmentsApiExceptionHandler {
	
	@ExceptionHandler({EquipmentsApiException.class})
	public ResponseEntity<Object> handleEquipmentsApiException(EquipmentsApiException ex) {
		return new ResponseEntity<Object>(ex, HttpStatus.BAD_REQUEST);
	}
	
}
