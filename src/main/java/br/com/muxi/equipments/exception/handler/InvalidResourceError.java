package br.com.muxi.equipments.exception.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.MethodArgumentNotValidException;

public class InvalidResourceError extends EquipmentsApiError {
	
	protected List<InvalidFieldValueError> fieldErrors;

	public InvalidResourceError(MethodArgumentNotValidException ex) {
		super(ex);
		this.message = "Invalid arguments.";
		this.fieldErrors = ex.getBindingResult().getFieldErrors().stream()
				.map(fieldError -> new InvalidFieldValueError(fieldError.getField(), fieldError.getDefaultMessage()))
				.collect(Collectors.toList());
	}
	
	public List<InvalidFieldValueError> getFieldErrors() {
		return this.fieldErrors;
	}

}
