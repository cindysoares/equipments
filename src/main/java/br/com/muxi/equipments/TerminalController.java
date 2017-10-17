package br.com.muxi.equipments;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.muxi.equipments.exception.EquipmentsApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/terminal")
@Api(value = "Terminal Resource", description ="Endpoint for Terminal operations")
public class TerminalController {
	
	@Autowired
	private TerminalRepository repository;
	
	@ApiOperation(value = "Search a terminal by logic")
	@GetMapping(value="/{logic}", produces=MediaType.APPLICATION_JSON_VALUE)
	public Terminal getTerminal(@PathVariable Integer logic) throws EquipmentsApiException {
		Terminal terminal = repository.findByLogic(logic);
		// TODO should throws an specific exception.
		if(terminal==null) {
			throw new EquipmentsApiException("The Terminal with logic " + logic + " doesn´t exists.");
		}
		return terminal;
    }
	
	@ApiOperation(value = "Search terminals by serial, model and/or version. Returns all terminals available without parameters.")
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
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
	
	@ApiOperation(value = "Add a terminal", produces=MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value="", consumes=MediaType.TEXT_HTML_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public Terminal insertTerminal(
			@ApiParam(value="A string value with all terminal attributes separated by ';' in the following order: [logic];[serial];[model];[sam];[ptid];[plat];[version];[mxr];[mxf];[verfm]", required=true, 
			example="44332211;123;PWWIN;0;F04A2E4088B;4;8.00b3;0;16777216;PWWIN") 
			@RequestBody String terminalValues) throws EquipmentsApiException {		
		
		Terminal terminalToInsert = Terminal.valueOf(terminalValues);
		
		// TODO should throws an specific exception.
		if(repository.exists(terminalToInsert.getLogic())) {
			throw new EquipmentsApiException("A Terminal with the logic " + terminalToInsert.getLogic() + " already exists.");
		}
		return repository.save(terminalToInsert);
	}
	
	@ApiOperation(value = "Update a terminal")
	@PutMapping(value="/{logic}", produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public Terminal updateTerminal(@PathVariable Integer logic, @Validated @RequestBody Terminal terminalValues) throws EquipmentsApiException {
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
