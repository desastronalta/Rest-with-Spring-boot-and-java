package br.com.erudio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.model.Person;
import br.com.erudio.services.PersonServices;

@RestController
@RequestMapping("/person")
//requestmapping a nivel de controle serve para que todas as operações, relacionadas a person
//sejam feitas nessa classe.
public class PersonController {
	
	@Autowired
	// esta notação serve para indicar ao inpringboot onde tera um auto complete do services.
	private PersonServices service;
	
	@RequestMapping(value = "/{id}",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE
			)
	public Person findById(@PathVariable(value = "id") String id) {
		
	return service.findById(id);
	}
}
