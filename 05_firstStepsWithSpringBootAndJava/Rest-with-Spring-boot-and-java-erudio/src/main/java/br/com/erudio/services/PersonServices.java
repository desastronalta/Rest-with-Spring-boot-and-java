package br.com.erudio.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import br.com.erudio.controllers.PersonController;
import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exceptions.ResourceNotFoundEXception;
import br.com.erudio.mapper.custom.PersonMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;

@Service
// Tem como funcionalidade instanciar o objeto dentro da classe;
public class PersonServices {
	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	PersonRepository repository;
	
	@Autowired
	PersonMapper mapper;
	public List<PersonVO> findAll() {

		logger.info("Finding all Persons");

		var persons = mapper.convertListEntityToVO(repository.findAll());
		persons.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		return persons;
	}

	public PersonVO findById(Long id) {

		logger.info("Finding one Person");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundEXception("No records found for this ID."));
		PersonVO vo = mapper.convertEntityToVO(entity);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
		
	}

	public PersonVO create(Person person) {
		// quando houver acesso a base de dados aqui vira a implementação do mesmo.
		logger.info("Creating one person");
		
		var vo = mapper.convertEntityToVO(repository.save(person));
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	public PersonVO update(PersonVO person) {
		logger.info("Updating one person");
		var entity = repository.findById(person.getKey())
				.orElseThrow(() -> new ResourceNotFoundEXception("No records found for this ID."));
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAdress(person.getAdress());
		entity.setGender(person.getGender());

		var vo = mapper.convertEntityToVO(repository.save(entity));

		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	public void delete(Long id) {
		// quando houver acesso a base de dados aqui vira a implementação do mesmo.
		logger.info("Deleting one person");
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundEXception("No records found for this ID."));
		repository.delete(entity);

	}
}
