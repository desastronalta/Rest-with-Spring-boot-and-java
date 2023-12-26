package br.com.erudio.unittest.mockito.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.erudio.mock.person.MockPerson;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;
import br.com.erudio.services.PersonServices;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServic1esTest {

	MockPerson input;
	
	@InjectMocks
	private PersonServices service;
	@Mock
	PersonRepository repository;
	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockPerson();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindAll() {
		fail("Not yet implemented");
	}

	@Test
	void testFindById() {
		Person person = input.mockEntity();
		person.setId(1L);
		
//esse método passa para o teste que quando formos chamar o metodo findById ele devera retornar o person instanciado acima.
		when(repository.findById(1L)).thenReturn(Optional.of(person));
		var result =  service.findById(1L);
		assertNotNull(result);
		assertNotNull(result.getLinks());
		assertNotNull(result.getKey());
		System.out.println(result.toString());
		assertTrue(result.toString().contains(""));
		
	}

	@Test
	void testCreate() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

}
