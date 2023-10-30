package br.com.erudio.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.exceptions.ResourceNotFoundEXception;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;

@Service
// Tem como funcionalidade instanciar o objeto dentro da classe;
public class PersonServices {
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	
	public List<Person> findAll() {
		
		logger.info("Finding all Persons");
		
		return repository.findAll() ;
	}
	
	public Person findById(long id) {
		
		
		logger.info("Finding one Person");
		
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundEXception(
				"No records found for this ID."));
	}
	public Person create(Person person) {
		//quando houver acesso a base de dados aqui vira a implementação do mesmo.
		logger.info("Creating one person");
		
		return repository.save(person);
	}
	public Person update(Person person) {
		//quando houver acesso a base de dados aqui vira a implementação do mesmo.
		logger.info("Updating one person");
		var entity = repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundEXception(
				"No records found for this ID."));
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAdress(person.getAdress());
		entity.setGender(person.getGender());
		
		return repository.save(entity);
	}
	
	public void delete(long id) {
		//quando houver acesso a base de dados aqui vira a implementação do mesmo.
		logger.info("Deleting one person");
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundEXception(
				"No records found for this ID."));
		repository.delete(entity);
		
	}
}
