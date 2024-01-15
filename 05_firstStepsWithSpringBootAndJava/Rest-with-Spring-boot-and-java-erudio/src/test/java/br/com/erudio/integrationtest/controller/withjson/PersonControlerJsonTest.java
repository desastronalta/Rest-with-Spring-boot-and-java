package br.com.erudio.integrationtest.controller.withjson;

import static io.restassured.RestAssured.given;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.erudio.configs.TestConfigs;
import br.com.erudio.integrationtest.vo.AccountCredentialsVO;
import br.com.erudio.integrationtest.vo.PersonVO;
import br.com.erudio.integrationtest.vo.TokenVO;
import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
class PersonControlerJsonTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	
	private static PersonVO person;
	
	@BeforeAll
	public static void setUp() {
		objectMapper = new ObjectMapper();
/*
 * no caso abaixo ocorre uma falha esperada pois o VO nao reconhece as propriedades do HATOAS, por isso a falha por propriedades
 * desconhecidas foi desativado.
 */
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		person = new PersonVO();
	}
	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("rafael", "admin123");
		
		var accessToken = given()
				.basePath("/auth/signin")
				.port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(user)
				.when()
				.post()
				.then()
				.statusCode(200)
				.extract()
				.body()
				.as(TokenVO.class)
				.getAccesToken();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "bearer " + accessToken)
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	@Test
	@Order(1)
	public void testeCreate() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		
		
		var content = 
			given().spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.body(person)
				.when()
				.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.asString();
		PersonVO pessoaCriada = objectMapper.readValue(content, PersonVO.class);
		person = pessoaCriada;
		assertNotNull(pessoaCriada.getId());
		assertNotNull(pessoaCriada.getFirstName());
		assertNotNull(pessoaCriada.getLastName());
		assertNotNull(pessoaCriada.getAdress());
		assertNotNull(pessoaCriada.getGender());
		
		assertTrue(pessoaCriada.getId() > 0);
		
		assertEquals("Juliano",pessoaCriada.getFirstName());
		assertEquals("Almeida",pessoaCriada.getLastName());
		assertEquals("Alagoas",pessoaCriada.getAdress());
		assertEquals("Male",pessoaCriada.getGender());
	}
	@Test
	@Order(2)
	public void testeUpdate() throws JsonMappingException, JsonProcessingException {
		person.setFirstName("Fabiano");
		person.setLastName("Aragão");
		person.setAdress("Cubatão");
		
		var content = 
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(person)
				.when()
				.post()
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();
		PersonVO pessoaCriada = objectMapper.readValue(content, PersonVO.class);
		person = pessoaCriada;
		assertNotNull(pessoaCriada.getId());
		assertNotNull(pessoaCriada.getFirstName());
		assertNotNull(pessoaCriada.getLastName());
		assertNotNull(pessoaCriada.getAdress());
		assertNotNull(pessoaCriada.getGender());
		
		assertEquals(person.getId(),pessoaCriada.getId());
		
		assertEquals("Fabiano",pessoaCriada.getFirstName());
		assertEquals("Aragão",pessoaCriada.getLastName());
		assertEquals("Cubatão",pessoaCriada.getAdress());
		assertEquals("Male",pessoaCriada.getGender());
	}
	@Test
	@Order(3)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.pathParam("id", person.getId())
					.when()
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();
		
		PersonVO pessoaBuscada = objectMapper.readValue(content, PersonVO.class);
		person = pessoaBuscada;
		assertNotNull(pessoaBuscada.getId());
		assertNotNull(pessoaBuscada.getFirstName());
		assertNotNull(pessoaBuscada.getLastName());
		assertNotNull(pessoaBuscada.getAdress());
		assertNotNull(pessoaBuscada.getGender());
		
		assertEquals(person, pessoaBuscada);
		
		assertEquals("Fabiano",pessoaBuscada.getFirstName());
		assertEquals("Aragão",pessoaBuscada.getLastName());
		assertEquals("Cubatão",pessoaBuscada.getAdress());
		assertEquals("Male",pessoaBuscada.getGender());
	}
	@Test
	@Order(4)
	public void testDelete() {
		given().spec(specification)
		.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.pathParam("id", person.getId())
			.when()
			.delete()
		.then()
			.statusCode(204);
	}
	@Test
	@Order(5)
	public void testFindAll() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.when()
					.get()
				.then()
					.statusCode(200)
						.extract()
						.body()
							.asString();
		
		List<PersonVO> people = objectMapper.readValue(content, new TypeReference<List<PersonVO>>() {});
PersonVO foundPersonOne = people.get(0);
		
		assertNotNull(foundPersonOne.getId());
		assertNotNull(foundPersonOne.getFirstName());
		assertNotNull(foundPersonOne.getLastName());
		assertNotNull(foundPersonOne.getAdress());
		assertNotNull(foundPersonOne.getGender());
		
		assertEquals(1, foundPersonOne.getId());
		
		assertEquals("Ayrton", foundPersonOne.getFirstName());
		assertEquals("Senna", foundPersonOne.getLastName());
		assertEquals("São Paulo", foundPersonOne.getAdress());
		assertEquals("Male", foundPersonOne.getGender());
		
		PersonVO foundPersonSix = people.get(5);
		
		assertNotNull(foundPersonSix.getId());
		assertNotNull(foundPersonSix.getFirstName());
		assertNotNull(foundPersonSix.getLastName());
		assertNotNull(foundPersonSix.getAdress());
		assertNotNull(foundPersonSix.getGender());
		
		assertEquals(9, foundPersonSix.getId());
		
		assertEquals("Nelson", foundPersonSix.getFirstName());
		assertEquals("Mvezo", foundPersonSix.getLastName());
		assertEquals("Mvezo – South Africa", foundPersonSix.getAdress());
		assertEquals("Male", foundPersonSix.getGender());
	}
	
	private void mockPerson() {
		person.setFirstName("Juliano");
		person.setLastName("Almeida");
		person.setAdress("Alagoas");
		person.setGender("Male");
	}

}
