package br.com.erudio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.vo.v1.*;
import br.com.erudio.model.Person;
import br.com.erudio.services.PersonServices;

@RestController
@RequestMapping("/api/person/v1")
//requestmapping a nivel de controle serve para que todas as operações, relacionadas a person
//sejam feitas nessa classe.
public class PersonController {
	
	@Autowired
	// esta notação serve para indicar ao inpringboot onde tera um auto complete do services.
	private PersonServices service;
	
	@GetMapping(value = "/{id}",
			produces= { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
			)
	public PersonVO findById(@PathVariable(value = "id") Long id) {
		
	return service.findById(id);
	}
	@GetMapping(
			produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
			)
			public List<PersonVO> findAll() {
		
	return service.findAll();
	}
	// consumes serve para indicar que a aplicação recebe um tipo de dado.
	@PostMapping(
			consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
			produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
			)
	public PersonVO create(@RequestBody Person person) {
		
		return service.create(person);
	}
	@PutMapping(
			consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
			produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
			)
	public PersonVO update(@RequestBody PersonVO person) {
		
		return service.update(person);
	}
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
