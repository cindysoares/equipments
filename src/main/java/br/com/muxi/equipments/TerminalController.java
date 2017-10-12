package br.com.muxi.equipments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1.0/terminal")
public class TerminalController {
	
	@Autowired
	private TerminalRepository repository;
	
	@GetMapping("/{logic}")
	public Terminal getTerminal(@PathVariable Integer logic) {
		return new Terminal(logic, null, null, null, null, null, null, null, null, null);
    }
	
	@PostMapping(value="", consumes=MediaType.TEXT_HTML_VALUE)
	public Terminal insertTerminal(@RequestBody String terminalValues) {
		String[] values = terminalValues.split(";");
		Terminal terminalToInsert =  new Terminal(Integer.valueOf(values[0]), values[1], values[2],
				Integer.valueOf(values[3]), values[4], Integer.valueOf(values[5]),
				values[6], Integer.valueOf(values[7]), Integer.valueOf(values[8]), 
				values[9]);
		return repository.save(terminalToInsert);
	}
	
	@PutMapping(value="/{logic}", consumes=MediaType.TEXT_HTML_VALUE)
	public Terminal updateTerminal(@PathVariable Integer logic, @RequestBody String terminalValues) {
		Terminal terminalToUpdate = new Terminal(logic, null, null, null, null, null, null, null, null, null);
		
		String[] values = terminalValues.split(";");
		terminalToUpdate.setSerial(values[1]);
		terminalToUpdate.setModel(values[2]);
		terminalToUpdate.setSam(Integer.valueOf(values[3]));
		terminalToUpdate.setPtid(values[4]);
		terminalToUpdate.setPlat(Integer.valueOf(values[5]));
		terminalToUpdate.setVersion(values[6]);
		terminalToUpdate.setMxr(Integer.valueOf(values[7]));
		terminalToUpdate.setMxf(Integer.valueOf(values[8]));
		terminalToUpdate.setVerfm(values[9]);

		return terminalToUpdate;
	}

}
