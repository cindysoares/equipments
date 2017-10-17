package br.com.muxi.equipments.schema;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import br.com.muxi.equipments.Terminal;
import br.com.muxi.equipments.exception.EquipmentsApiException;

public class SchemaValidatorTest {
	
    private static Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void validTerminal() throws EquipmentsApiException {
		Terminal terminal = Terminal.valueOf("1234;123;PWWIN;0;FffffffFFFF;1;8.00b5;0;16777216;PWWIN");
		Set<ConstraintViolation<Terminal>> violations = validator.validate(terminal);
        assertTrue(violations.isEmpty());
	}
	
	@Test
	public void invalidTerminal() throws EquipmentsApiException {		
		Terminal terminal = Terminal.valueOf(";;;-1;FffffffFFFF;1;8.00b5;0;16777216;PWWIN");
		Set<ConstraintViolation<Terminal>> violations = validator.validate(terminal);
        assertFalse(violations.isEmpty());
	}
	
	@Test
	public void validTerminalWithNullValues() throws EquipmentsApiException {
		Terminal terminal = Terminal.valueOf("54321;123;PWWIN;;;;8.00b3;;;");
		Set<ConstraintViolation<Terminal>> violations = validator.validate(terminal);
        assertTrue(violations.isEmpty());
	}
	
	

}
