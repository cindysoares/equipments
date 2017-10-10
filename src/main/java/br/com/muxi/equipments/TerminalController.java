package br.com.muxi.equipments;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1.0/terminal")
public class TerminalController {
	
	@GetMapping("/{logic}")
	public Terminal getTerminal(@PathVariable Integer logic) {
		return new Terminal(logic);
    }

}
