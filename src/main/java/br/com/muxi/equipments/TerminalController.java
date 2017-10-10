package br.com.muxi.equipments;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1.0/terminal")
public class TerminalController {
	
	@GetMapping("/{logic}")
	public Terminal getTerminal(@PathVariable Integer logic) {
		return new Terminal(logic);
    }
	
	@PostMapping(value="", consumes=MediaType.TEXT_PLAIN_VALUE)
	public Terminal insertTerminal(@RequestBody String terminalValues) {
		String[] values = terminalValues.split(";");
		return new Terminal(Integer.valueOf(values[0]));
	}
	
	@PostMapping(value="/{logic}", consumes=MediaType.TEXT_PLAIN_VALUE)
	public Terminal insertTerminal(@PathVariable Integer logic, @RequestBody String terminalValues) {
		String[] values = terminalValues.split(";");
		return new Terminal(Integer.valueOf(values[0]));
	}

}
