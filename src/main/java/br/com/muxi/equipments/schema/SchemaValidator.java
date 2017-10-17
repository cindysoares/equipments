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
		
		try {
			return isValid(object);
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

	private boolean isValid(Object terminal) throws IllegalArgumentException, ProcessingException {
		return isValid(new ObjectMapper().valueToTree(terminal));
	}
	
	private boolean isValid(JsonNode instance) throws ProcessingException {
		ProcessingReport report = jsonSchema.validate(instance);
		return report.isSuccess();
	}
}
