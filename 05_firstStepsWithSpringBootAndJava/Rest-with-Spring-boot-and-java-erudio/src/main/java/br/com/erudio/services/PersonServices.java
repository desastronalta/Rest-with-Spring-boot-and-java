package br.com.erudio.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import br.com.erudio.model.Person;

@Service
// Tem como funcionalidade instanciar o objeto dentro da classe;
public class PersonServices {
	private final AtomicLong counter = new AtomicLong();
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	public List<Person> findAll() {
		
		logger.info("Finding all Persons");
		
		List<Person> persons = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			Person person = mockPerson(i);
			persons.add(person);
		}
		return persons ;
	}
	private Person mockPerson(int i) {
		Person person = new Person();
		person .setId(counter.incrementAndGet());
		person.setFirstName("parsonName" + i);
		person.setLastName("lastName" + i);
		person.setAdress("adress" + i);
		person.setGender("gender" + i);
		return person;
	}
	public Person findById(String id) {
		
		
		logger.info("Finding one Person");
		
		Person person =  new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("Andre");
		person.setLastName("Batista");
		person.setAdress("Floripa, Sao paulo");
		person.setGender("Male");
		return person;
	}
	public Person create(Person person) {
		//quando houver acesso a base de dados aqui vira a implementação do mesmo.
		logger.info("Creating one person");
		
		return person;
	}
	public Person update(Person person) {
		//quando houver acesso a base de dados aqui vira a implementação do mesmo.
		logger.info("Updating one person");
		
		return person;
	}
	
	public void delete(String id) {
		//quando houver acesso a base de dados aqui vira a implementação do mesmo.
		logger.info("Deleting one person");
		
	}
}
