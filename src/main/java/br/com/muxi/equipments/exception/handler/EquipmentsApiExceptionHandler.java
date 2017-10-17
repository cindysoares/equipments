package br.com.muxi.equipments.exception.handler;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;

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
	
	@ExceptionHandler({RollbackException.class})
	public ResponseEntity<Object> handleValidationException(final RollbackException ex, WebRequest request) {
		Throwable cause = ex.getCause();
		if (cause instanceof ConstraintViolationException) {
			return new ResponseEntity<Object>(new InvalidResourceError((ConstraintViolationException) cause), HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<Object>(new EquipmentsApiError(ex), HttpStatus.BAD_REQUEST);
		}
	} 
	
}
