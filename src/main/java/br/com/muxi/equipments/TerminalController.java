package br.com.muxi.equipments;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.muxi.equipments.exception.EquipmentsApiException;

@RestController
@RequestMapping("/terminal")
public class TerminalController {
	
	@Autowired
	private TerminalRepository repository;
	
	@GetMapping("/{logic}")
	public Terminal getTerminal(@PathVariable Integer logic) throws EquipmentsApiException {
		Terminal terminal = repository.findByLogic(logic);
		// TODO should throws an specific exception.
		if(terminal==null) {
			throw new EquipmentsApiException("The Terminal with logic " + logic + " doesn´t exists.");
		}
		return terminal;
    }
	
	@GetMapping
	public List<Terminal> getTerminalList(
			@RequestParam(value="serial", required=false) String serial,
			@RequestParam(value="model", required=false) String model, 
			@RequestParam(value="version", required=false) String version) {
		Terminal example = new Terminal();
		example.setSerial(serial);
		example.setModel(model);
		example.setVersion(version);
		return repository.findAll(Example.of(example));
	}
	
	@PostMapping(value="", consumes=MediaType.TEXT_HTML_VALUE)
	public Terminal insertTerminal(@RequestBody String terminalValues) throws EquipmentsApiException {		
		String[] values = terminalValues.split(";");
		Integer logic = Integer.valueOf(values[0]);
		
		// TODO should throws an specific exception.
		if(repository.exists(logic)) {
			throw new EquipmentsApiException("A Terminal with the logic " + logic + " already exists.");
		}
		Terminal terminalToInsert =  new Terminal(logic, values[1], values[2],
				Integer.valueOf(values[3]), values[4], Integer.valueOf(values[5]),
				values[6], Integer.valueOf(values[7]), Integer.valueOf(values[8]), 
				values[9]);
		return repository.save(terminalToInsert);
	}
	
	@PutMapping(value="/{logic}")
	public Terminal updateTerminal(@PathVariable Integer logic, @Valid @RequestBody Terminal terminalValues) throws EquipmentsApiException {
		Terminal terminalToUpdate = repository.findByLogic(logic);
		
		// TODO should throws an specific exception.
		if(terminalToUpdate == null) {
			throw new EquipmentsApiException("The Terminal with logic " + logic + " doesn´t exists.");
		}
		
		terminalToUpdate.setSerial(terminalValues.getSerial());
		terminalToUpdate.setModel(terminalValues.getModel());
		terminalToUpdate.setSam(terminalValues.getSam());
		terminalToUpdate.setPtid(terminalValues.getPtid());
		terminalToUpdate.setPlat(terminalValues.getPlat());
		terminalToUpdate.setVersion(terminalValues.getVersion());
		terminalToUpdate.setMxr(terminalValues.getMxr());
		terminalToUpdate.setMxf(terminalValues.getMxf());
		terminalToUpdate.setVerfm(terminalValues.getVerfm());

		return repository.save(terminalToUpdate);
	}

}
