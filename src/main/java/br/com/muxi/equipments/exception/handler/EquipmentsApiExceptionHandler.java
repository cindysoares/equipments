package br.com.muxi.equipments.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import br.com.muxi.equipments.exception.EquipmentsApiException;

@ControllerAdvice
public class EquipmentsApiExceptionHandler {
	
	@ExceptionHandler({EquipmentsApiException.class})
	public ResponseEntity<Object> handleEquipmentsApiException(EquipmentsApiException ex) {
		return new ResponseEntity<Object>(new EquipmentsApiError(ex), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseEntity<Object> handleValidationException(final MethodArgumentNotValidException ex, WebRequest request) {
		return new ResponseEntity<Object>(new InvalidResourceError(ex), HttpStatus.BAD_REQUEST);
	}
	
}
