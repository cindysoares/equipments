package br.com.muxi.equipments;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.is;

import org.junit.Test;

public class TerminalTest {
	
	@Test
	public void testValueOfWithNullValues() throws Exception {
		Terminal result = Terminal.valueOf("");
		assertThat(result.getLogic(), is(nullValue()));
		assertThat(result.getSerial(), is(nullValue()));
		assertThat(result.getModel(), is(nullValue()));
		assertThat(result.getSam(), is(nullValue()));
		assertThat(result.getPtid(), is(nullValue()));
		assertThat(result.getPlat(), is(nullValue()));
		assertThat(result.getVersion(), is(nullValue()));
		assertThat(result.getMxr(), is(nullValue()));
		assertThat(result.getMxf(), is(nullValue()));
		assertThat(result.getVerfm(), is(nullValue()));
	}
	
	@Test
	public void testValueOfWithAllValues() throws Exception {
		Terminal result = Terminal.valueOf("1234;123;PWWIN;0;FffffffFFFF;1;8.00b5;0;16777216;WWWW");
		assertThat(result.getLogic(), is(1234));
		assertThat(result.getSerial(), is("123"));
		assertThat(result.getModel(), is("PWWIN"));
		assertThat(result.getSam(), is(0));
		assertThat(result.getPtid(), is("FffffffFFFF"));
		assertThat(result.getPlat(), is(1));
		assertThat(result.getVersion(), is("8.00b5"));
		assertThat(result.getMxr(), is(0));
		assertThat(result.getMxf(), is(16777216));
		assertThat(result.getVerfm(), is("WWWW"));
	}

}
