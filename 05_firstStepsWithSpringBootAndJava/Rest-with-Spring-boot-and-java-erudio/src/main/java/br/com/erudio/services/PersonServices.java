package br.com.erudio.services;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import br.com.erudio.model.Person;

@Service
// Tem como funcionalidade instanciar o objeto dentro da classe;
public class PersonServices {
	private final AtomicLong counter = new AtomicLong();
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
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
}
