package br.com.muxi.equipments;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(
  webEnvironment = WebEnvironment.RANDOM_PORT,
  classes = EquipmentsApplication.class)
@AutoConfigureMockMvc
public class TerminalControllerTest {

	@Autowired
	private MockMvc mvc;
    
	@Test
	public void testGetTerminal() throws Exception {
		mvc.perform(get("/v1.0/terminal/1234")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("logic", is(1234)));		
	}

	@Test
	public void testDelete() throws Exception {
		mvc.perform(delete("/v1.0/terminal/1234"))
		.andExpect(status().isMethodNotAllowed());
	}	
	
	@Test
	public void testNewTerminal_withJSON() throws Exception {
		mvc.perform(post("/v1.0/terminal")
			.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isUnsupportedMediaType());
	}
	
	@Test
	public void testNewTerminal() throws Exception {
		mvc.perform(post("/v1.0/terminal")
			.content("44332211;123;PWWIN;0;F04A2E4088B;4;8.00b3;0;16777216;PWWIN")
			.contentType(MediaType.TEXT_PLAIN))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("logic", is(44332211)));
	}


}
