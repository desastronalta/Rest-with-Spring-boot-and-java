package br.com.erudio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
/*
 * @CrossOrigin no nivel de controler serve para passar que todas as operações serão liberadas apenas para os dominios
 * declarados em "origins"
 */
@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "Endpoints for menager peoples")
//requestmapping a nivel de controle serve para que todas as operações, relacionadas a person
//sejam feitas nessa classe.
public class PersonController {
	
	@Autowired
	// esta notação serve para indicar ao inpringboot onde tera um auto complete do services.
	private PersonServices service;
	
	/*
	 * na linha de código abaixo estamos especificando que o acesso sera somente para o dominio descrito abaixo.
	 */
	@CrossOrigin(origins = "http://localhost:8080")
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
			public ResponseEntity<PagedModel<EntityModel<PersonVO>>> findAll(
					@RequestParam(value = "page" , defaultValue = "0") Integer page,
					@RequestParam(value = "size" , defaultValue = "12") Integer size,
					@RequestParam(value = "direction" , defaultValue = "asc") String direction,
					@RequestParam(value = "field" , defaultValue = "firstName") String field
					){
				var sortDirection = "desc".equalsIgnoreCase(direction) 
						? Direction.DESC : Direction.ASC;
				
				String choseField = service.validateField(field);
				Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, choseField));
					
				return ResponseEntity.ok(service.findAll(pageable));
	}
	
	@GetMapping(value = "/findPersonByName/{firstName}" ,
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
	public ResponseEntity<PagedModel<EntityModel<PersonVO>>> findPersonByName(
			@PathVariable(value = "firstName") String firstName,
			@RequestParam(value = "page" , defaultValue = "0") Integer page,
			@RequestParam(value = "size" , defaultValue = "12") Integer size,
			@RequestParam(value = "direction" , defaultValue = "asc") String direction
			){
		var sortDirection = "desc".equalsIgnoreCase(direction) 
				? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
		
		return ResponseEntity.ok(service.findByName(firstName, pageable));
	}
	
	// consumes serve para indicar que a aplicação recebe um tipo de dado.
	@CrossOrigin(origins = {"http://localhost:8080", "http://erudio.com.br"})
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
	
	@CrossOrigin(origins = "http://localhost:8080")
	@PatchMapping(value = "/{id}",
			produces= { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
			)
	@Operation(summary = "FindById", description = "disabling person by Id", 
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
	public PersonVO disablePerson(@PathVariable(value = "id") Long id) {
		
	return service.disabelPerson(id);
	}
	
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "delete", description = "delete a people", 
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
