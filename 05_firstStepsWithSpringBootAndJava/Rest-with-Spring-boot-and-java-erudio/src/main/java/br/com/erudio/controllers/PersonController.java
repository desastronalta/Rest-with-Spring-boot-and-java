package br.com.erudio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.model.Person;
import br.com.erudio.services.PersonServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "Endpoints for menager peoples")
//requestmapping a nivel de controle serve para que todas as operações, relacionadas a person
//sejam feitas nessa classe.
public class PersonController {
	
	@Autowired
	// esta notação serve para indicar ao inpringboot onde tera um auto complete do services.
	private PersonServices service;
	
	@GetMapping(value = "/{id}",
			produces= { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
			)
	@Operation(summary = "FindById", description = "find peoples by Id", 
		tags = {"People"}, 
		responses = {
			@ApiResponse(description = "Success", responseCode= "200", 
						content = 
							@Content(schema = @Schema(implementation = PersonVO.class))
				),
			@ApiResponse(description = "Bad request", responseCode= "400", content = @Content),
			@ApiResponse(description = "No Content", responseCode= "204", content = @Content),
			@ApiResponse(description = "Anauthorized", responseCode= "401", content = @Content),
			@ApiResponse(description = "Not found", responseCode= "404", content = @Content),
			@ApiResponse(description = "Internal server error", responseCode= "500", content = @Content),
			})
	public PersonVO findById(@PathVariable(value = "id") Long id) {
		
	return service.findById(id);
	}
	@GetMapping(
			produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
			)
	@Operation(summary = "FindAll", description = "find all peoples", 
	tags = {"People"}, 
	responses = {
			@ApiResponse(description = "Success", responseCode= "200", 
					content = {
						@Content(
								mediaType = "application/json",
								array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
								)
									
							}),
			
			@ApiResponse(description = "Bad request", responseCode= "400", content = @Content),
			@ApiResponse(description = "Anauthorized", responseCode= "401", content = @Content),
			@ApiResponse(description = "Not found", responseCode= "404", content = @Content),
			@ApiResponse(description = "Internal server error", responseCode= "500", content = @Content),
			})
			public List<PersonVO> findAll() {
		
	return service.findAll();
	}
	// consumes serve para indicar que a aplicação recebe um tipo de dado.
	@PostMapping(
			consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
			produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
			)
	@Operation(summary = "create", description = "Create a people", 
	tags = {"People"}, 
	responses = {
		@ApiResponse(description = "Success", responseCode= "200", 
					content = 
						@Content(schema = @Schema(implementation = PersonVO.class))
			),
		@ApiResponse(description = "Bad request", responseCode= "400", content = @Content),
		@ApiResponse(description = "Anauthorized", responseCode= "401", content = @Content),
		@ApiResponse(description = "Internal server error", responseCode= "500", content = @Content),
		})
	public PersonVO create(@RequestBody Person person) {
		
		return service.create(person);
	}
	@PutMapping(
			consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
			produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
			)
	@Operation(summary = "update", description = "Update the people data by Id", 
	tags = {"People"}, 
	responses = {
		@ApiResponse(description = "Success", responseCode= "200", 
					content = 
						@Content(schema = @Schema(implementation = PersonVO.class))
			),
		@ApiResponse(description = "Bad request", responseCode= "400", content = @Content),
		@ApiResponse(description = "Anauthorized", responseCode= "401", content = @Content),
		@ApiResponse(description = "Not found", responseCode= "404", content = @Content),
		@ApiResponse(description = "Internal server error", responseCode= "500", content = @Content),
		})
	public PersonVO update(@RequestBody PersonVO person) {
		
		return service.update(person);
	}
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "create", description = "Create a people", 
	tags = {"People"}, 
	responses = {
		@ApiResponse(description = "No content", responseCode= "204", 
					content = @Content
			),
		@ApiResponse(description = "Bad request", responseCode= "400", content = @Content),
		@ApiResponse(description = "Anauthorized", responseCode= "401", content = @Content),
		@ApiResponse(description = "Not found", responseCode= "404", content = @Content),
		@ApiResponse(description = "Internal server error", responseCode= "500", content = @Content),
		})
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
