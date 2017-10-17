package br.com.muxi.equipments;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.muxi.equipments.exception.EquipmentsApiException;

@RunWith(SpringRunner.class)
@SpringBootTest(
  webEnvironment = WebEnvironment.RANDOM_PORT,
  classes = EquipmentsApplication.class)
@AutoConfigureMockMvc
public class TerminalControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private TerminalRepository repository;
	
	@Before
	public void createData() throws EquipmentsApiException {
		repository.save(Terminal.valueOf("1234;123;PWWIN;0;FffffffFFFF;1;8.00b5;0;16777216;PWWIN"));
		repository.save(Terminal.valueOf("4567;456;PWWIN;0;XXXXXXXXXXX;1;5.1;0;16777216;PWWIN"));
		repository.save(Terminal.valueOf("2345;123;NIWWP;0;YYYYYYYYYYY;1;0.2;0;16777216;PWWIN"));
		repository.save(Terminal.valueOf("7890;789;NIWWP;0;YYYYYYYYYYY;1;8.00b5;0;16777216;PWWIN"));
	}
	
	@After
	public void deleteData() {
		repository.deleteAll();
	}
    
	@Test
	public void testGetTerminal() throws Exception {
		mvc.perform(get("/terminal/1234"))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("logic", is(1234)))
		.andExpect(jsonPath("serial", is("123")))
		.andExpect(jsonPath("model", is("PWWIN")))
		.andExpect(jsonPath("sam", is(0)))
		.andExpect(jsonPath("ptid", is("FffffffFFFF")))
		.andExpect(jsonPath("plat", is(1)))
		.andExpect(jsonPath("version", is("8.00b5")))
		.andExpect(jsonPath("mxr", is(0)))
		.andExpect(jsonPath("mxf", is(16777216)))
		.andExpect(jsonPath("VERFM", is("PWWIN")));		
	}
	
	@Test
	public void testGetTerminalByModel() throws Exception {
		mvc.perform(get("/terminal?model=PWWIN"))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].logic", is(1234)))
		.andExpect(jsonPath("$[0].model", is("PWWIN")))
		.andExpect(jsonPath("$[1].logic", is(4567)))
		.andExpect(jsonPath("$[1].model", is("PWWIN")));		
	}
	
	@Test
	public void testGetTerminalBySerial() throws Exception {
		mvc.perform(get("/terminal?serial=123"))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].logic", is(1234)))
		.andExpect(jsonPath("$[0].serial", is("123")))
		.andExpect(jsonPath("$[1].logic", is(2345)))
		.andExpect(jsonPath("$[1].serial", is("123")));		
	}
	
	@Test
	public void testGetTerminalByVersion() throws Exception {
		mvc.perform(get("/terminal?version=8.00b5"))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].logic", is(1234)))
		.andExpect(jsonPath("$[0].version", is("8.00b5")))
		.andExpect(jsonPath("$[1].logic", is(7890)))
		.andExpect(jsonPath("$[1].version", is("8.00b5")));		
	}
	
	
	@Test
	public void testGetAllTerminal() throws Exception {
		mvc.perform(get("/terminal"))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].logic", is(1234)))
		.andExpect(jsonPath("$[1].logic", is(2345)))
		.andExpect(jsonPath("$[2].logic", is(4567)))
		.andExpect(jsonPath("$[3].logic", is(7890)));		
	}	
	
	@Test
	public void testGetTerminal__withNonexistentLogic() throws Exception {
		mvc.perform(get("/terminal/54321"))
		.andExpect(status().isBadRequest())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("message", is("The Terminal with logic 54321 doesn´t exists.")));		
	}

	@Test
	public void testDelete() throws Exception {
		mvc.perform(delete("/terminal/1234"))
		.andExpect(status().isMethodNotAllowed());
	}	
	
	@Test
	public void testNewTerminal_withJSON() throws Exception {
		mvc.perform(post("/terminal")
			.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isUnsupportedMediaType());
	}
	
	@Test
	public void testNewTerminal() throws Exception {
		mvc.perform(post("/terminal")
			.content("54321;123;PWWIN;0;F04A2E4088B;4;8.00b3;0;16777216;PWWIN")
			.contentType(MediaType.TEXT_HTML))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("logic", is(54321)))
		.andExpect(jsonPath("serial", is("123")))
		.andExpect(jsonPath("model", is("PWWIN")))
		.andExpect(jsonPath("sam", is(0)))
		.andExpect(jsonPath("ptid", is("F04A2E4088B")))
		.andExpect(jsonPath("plat", is(4)))
		.andExpect(jsonPath("version", is("8.00b3")))
		.andExpect(jsonPath("mxr", is(0)))
		.andExpect(jsonPath("mxf", is(16777216)))
		.andExpect(jsonPath("VERFM", is("PWWIN")));
	}
	
	
	@Test
	public void testNewTerminalWithInvalidArguments() throws Exception {
		mvc.perform(post("/terminal")
			.content("54321;123;PWWIN;aaaaa;F04A2E4088B;aaaa;8.00b3;0;16777216;PWWIN")
			.contentType(MediaType.TEXT_HTML))
		.andExpect(status().isBadRequest())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("message", is("Invalid value for sam: aaaaa")));
	}
	
	@Test
	public void testNewTerminalWithSerialModelVersionValuesEqualsNullAndSamValueMinorZero() throws Exception {
		mvc.perform(post("/terminal")
			.content("54321;;;-1;F04A2E4088B;4;;0;16777216;PWWIN")
			.contentType(MediaType.TEXT_HTML))
		.andExpect(status().isBadRequest())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("fieldErrors", hasSize(5))); 
	}
	
	@Test
	public void testNewTerminalWithEmptyValues() throws Exception {
		mvc.perform(post("/terminal")
			.content("54321;123;PWWIN;;;;8.00b3;;;")
			.contentType(MediaType.TEXT_HTML))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("logic", is(54321)))
		.andExpect(jsonPath("serial", is("123")))
		.andExpect(jsonPath("model", is("PWWIN")))
		.andExpect(jsonPath("version", is("8.00b3")));
	}
	
	@Test
	public void testNewTerminal_withDuplicatedLogic() throws Exception {
		mvc.perform(post("/terminal")
			.content("1234;123;PWWIN;0;F04A2E4088B;4;8.00b3;0;16777216;PWWIN")
			.contentType(MediaType.TEXT_HTML))
		.andExpect(status().isBadRequest())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("message", is("A Terminal with the logic 1234 already exists.")));
	}
	
	@Test
	public void testUpdateTerminal() throws Exception {
		Terminal terminalValues = Terminal.valueOf("1234;987;PP;1;BBBBBBB;9;8.00c0;8;666666;WWWWW");
		
		mvc.perform(put("/terminal/1234")
				.content(new ObjectMapper().writeValueAsString(terminalValues))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("logic", is(1234)))
			.andExpect(jsonPath("serial", is("987")))
			.andExpect(jsonPath("model", is("PP")))
			.andExpect(jsonPath("sam", is(1)))
			.andExpect(jsonPath("ptid", is("BBBBBBB")))
			.andExpect(jsonPath("plat", is(9)))
			.andExpect(jsonPath("version", is("8.00c0")))
			.andExpect(jsonPath("mxr", is(8)))
			.andExpect(jsonPath("mxf", is(666666)))
			.andExpect(jsonPath("VERFM", is("WWWWW")));
	}
	
	@Test
	public void testUpdateTerminal_withNonexistentLogic() throws Exception {
		Terminal terminalValues = Terminal.valueOf("1234;987;PP;1;BBBBBBB;9;8.00c0;8;666666;WWWWW");
		
		mvc.perform(put("/terminal/54321")
				.content(new ObjectMapper().writeValueAsString(terminalValues))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("message", is("The Terminal with logic 54321 doesn´t exists.")));
	}
	
	@Test
	public void testUpdateTerminalWithSerialModelVersionValuesEqualsNullAndSamValueMinorZero() throws Exception {
		Terminal terminalValues = Terminal.valueOf("1234;;;-1;BBBBBBB;9;;8;666666;WWWWW");
		
		mvc.perform(put("/terminal/1234")
				.content(new ObjectMapper().writeValueAsString(terminalValues))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("fieldErrors", hasSize(4))); 

	}
}
