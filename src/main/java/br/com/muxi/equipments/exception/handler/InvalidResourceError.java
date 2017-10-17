package br.com.muxi.equipments.exception.handler;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.web.bind.MethodArgumentNotValidException;

public class InvalidResourceError extends EquipmentsApiError {
	
	private static final String INVALID_ARGUMENTS = "Invalid arguments.";
	
	protected List<InvalidFieldValueError> fieldErrors;
	
	public InvalidResourceError(ConstraintViolationException ex) {
		super(ex);
		this.message = INVALID_ARGUMENTS;
		this.fieldErrors = ex.getConstraintViolations().stream()
				.map(violation -> new InvalidFieldValueError(violation.getPropertyPath().toString(), violation.getMessage()))
				.collect(Collectors.toList());
	}

	public InvalidResourceError(MethodArgumentNotValidException ex) {
		super(ex);
		this.message = INVALID_ARGUMENTS;
		this.fieldErrors = ex.getBindingResult().getFieldErrors().stream()
				.map(fieldError -> new InvalidFieldValueError(fieldError.getField(), fieldError.getDefaultMessage()))
				.collect(Collectors.toList());
	}
	
	public List<InvalidFieldValueError> getFieldErrors() {
		return this.fieldErrors;
	}

}
