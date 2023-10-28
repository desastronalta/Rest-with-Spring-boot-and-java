package br.com.erudio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
	// consumes serve para indicar que a aplicação recebe um tipo de dado.
	@RequestMapping(
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE
			)
	public Person create(@RequestBody Person person) {
		
		return service.create(person);
	}
	@RequestMapping(
			method=RequestMethod.PUT,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE
			)
	public Person update(@RequestBody Person person) {
		
		return service.update(person);
	}
	@RequestMapping(value = "/{id}",
			method=RequestMethod.DELETE
			)
	public void delete(@PathVariable(value = "id") String id) {
		service.delete(id);
	}
	//caso nao passe parametros no mapping ele ira executar o método abaixo.
	@RequestMapping(
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE
			)
	public List<Person> findAll() {
		
	return service.findAll();
	}
}
