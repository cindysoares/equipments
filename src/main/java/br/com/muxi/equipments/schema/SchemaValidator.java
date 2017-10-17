package br.com.muxi.equipments.schema;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

public class SchemaValidator implements ConstraintValidator<ValidateSchema, Object> {

	private String jsonFile;
	private JsonSchema jsonSchema;
	
	@Override
	public void initialize(ValidateSchema validator) {
		jsonFile = validator.jsonFile();
	}
	
	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		loadJsonSchema(object);
		context.disableDefaultConstraintViolation();
		
		try {
			return isValid(new ObjectMapper().valueToTree(object), context);
		} catch (IllegalArgumentException | ProcessingException e) {
			throw new RuntimeException(e);
		} 
	}
	
	private void loadJsonSchema(Object o) {
		if(jsonSchema==null) {
			try {
				final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
				final JsonNode jsonNode = JsonLoader.fromURL(o.getClass().getResource(jsonFile));
				jsonSchema = factory.getJsonSchema(jsonNode);
			} catch (Exception e) {
				throw new RuntimeException("Could not load schema: " + jsonFile, e);
			}
		}
	}
	
	private boolean isValid(JsonNode instance, ConstraintValidatorContext context) throws ProcessingException {
		ProcessingReport report = jsonSchema.validate(instance);
		report.forEach(processingMessage -> context.buildConstraintViolationWithTemplate(processingMessage.getMessage()).addConstraintViolation());
		return report.isSuccess();
	}
}
