package br.com.erudio.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import br.com.erudio.controllers.PersonController;
import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exceptions.ResourceNotFoundEXception;
import br.com.erudio.mapper.custom.PersonMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;
import jakarta.transaction.Transactional;

@Service
// Tem como funcionalidade instanciar o objeto dentro da classe;
public class PersonServices {
	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	PersonRepository repository;
	
	@Autowired
	PagedResourcesAssembler<PersonVO> assembler;
	
	@Autowired
	PersonMapper mapper;
	public PagedModel<EntityModel<PersonVO>> findAll(Pageable pageable) {

		logger.info("Finding all Persons");
		
		var personPage = repository.findAll(pageable);
		var personVOsPage = personPage.map(p -> mapper.convertEntityToVO(p));
		
		personVOsPage.map(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		
		Link link = linkTo(
				methodOn(PersonController.class)
				.findAll(pageable.getPageNumber(),
						pageable.getPageSize(), 
						"asc", "firstName")).withSelfRel();
		return assembler.toModel(personVOsPage , link ) ;
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
	//estamos passando para o Spring data que este metódo esta sendo gerenciado de outra forma pois há dados que serão
	//modificados.
	@Transactional
	public PersonVO disabelPerson(Long id) {
		repository.disablePerson(id);
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundEXception("No records found for this ID."));
		PersonVO vo = mapper.convertEntityToVO(entity);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
		
	}

	public String validateField( String field) {
		if(field.equals("firstName") || field.equals("lastName") || field.equals("gender") || field.equals("adress")) {
			return field;
		}else return "firstName";
	}
}
