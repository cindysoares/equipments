package br.com.muxi.equipments.schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = SchemaValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateSchema {
	
	String message() default "Invalid object";

	String jsonFile();	

    Class<?>[] groups () default {};

    Class<? extends Payload>[] payload () default {};
	
}
