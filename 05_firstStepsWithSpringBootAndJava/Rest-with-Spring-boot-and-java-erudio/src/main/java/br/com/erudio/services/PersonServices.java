package br.com.erudio.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exceptions.ResourceNotFoundEXception;
import br.com.erudio.mapper.MyMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;

@Service
// Tem como funcionalidade instanciar o objeto dentro da classe;
public class PersonServices {
	private Logger logger = Logger.getLogger(PersonServices.class.getName());

	@Autowired
	PersonRepository repository;

	public List<PersonVO> findAll() {

		logger.info("Finding all Persons");

		return MyMapper.parseListObject(repository.findAll(), PersonVO.class);
	}

	public PersonVO findById(Long id) {

		logger.info("Finding one Person");

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundEXception("No records found for this ID."));
		return MyMapper.parseObject(entity, PersonVO.class);
	}

	public PersonVO create(Person person) {
		// quando houver acesso a base de dados aqui vira a implementação do mesmo.
		logger.info("Creating one person");
		var vo = MyMapper.parseObject(repository.save(person), PersonVO.class);
		return vo;
	}

	public PersonVO update(PersonVO person) {
		logger.info("Updating one person");
		var entity = repository.findById(person.getId())
				.orElseThrow(() -> new ResourceNotFoundEXception("No records found for this ID."));
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAdress(person.getAdress());
		entity.setGender(person.getGender());

		var vo = MyMapper.parseObject(repository.save(entity), PersonVO.class);

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
