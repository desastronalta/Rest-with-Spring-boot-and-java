package br.com.erudio.integrationtest.controller.withXml;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.erudio.configs.TestConfigs;
import br.com.erudio.integrationtest.vo.AccountCredentialsVO;
import br.com.erudio.integrationtest.vo.PersonVO;
import br.com.erudio.integrationtest.vo.TokenVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)

public class PersonControllerXmlTest {
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
				.contentType(TestConfigs.CONTENT_TYPE_XML)
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
			.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL)
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
	private void mockPerson() {
		person.setFirstName("Juliano");
		person.setLastName("Almeida");
		person.setAdress("Alagoas");
		person.setGender("Male");
	}
}
