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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.services.BooksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/book/v1")public class BooksController {

	@Autowired
	private BooksService service;
	
	@GetMapping(value = "/{id}",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@Operation(summary = "FindByName", description = "find book by autor name", 
	tags = {"Books"}, 
	responses = {
		@ApiResponse(description = "Success", responseCode= "200", 
					content = 
						@Content(schema = @Schema(implementation = BookVO.class))
			),
		@ApiResponse(description = "Bad request", responseCode= "400", content = @Content),
		@ApiResponse(description = "No Content", responseCode= "204", content = @Content),
		@ApiResponse(description = "Anauthorized", responseCode= "401", content = @Content),
		@ApiResponse(description = "Not found", responseCode= "404", content = @Content),
		@ApiResponse(description = "Internal server error", responseCode= "500", content = @Content),
		})
	public BookVO findById(@PathVariable(value = "id") Long id) {
		return service.findById(id);
	}
	
	@GetMapping(
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@Operation(summary = "FindAll", description = "find all book's ", 
	tags = {"Books"}, 
			responses = {
					@ApiResponse(description = "Success", responseCode= "200", 
							content = {
								@Content(
										mediaType = "application/json",
										array = @ArraySchema(schema = @Schema(implementation = BookVO.class))
										)
											
									}),
		@ApiResponse(description = "Bad request", responseCode= "400", content = @Content),
		@ApiResponse(description = "Anauthorized", responseCode= "401", content = @Content),
		@ApiResponse(description = "Not found", responseCode= "404", content = @Content),
		@ApiResponse(description = "Internal server error", responseCode= "500", content = @Content),
		})
	public ResponseEntity<PagedModel<EntityModel<BookVO>>> findAll(
			@RequestParam(value = "page" , defaultValue = "0") Integer page,
			@RequestParam(value = "size" , defaultValue = "12") Integer size,
			@RequestParam(value = "direction" , defaultValue = "asc") String direction,
			@RequestParam(value = "field" , defaultValue = "author") String field
			){
		var sortDirection = "desc".equalsIgnoreCase(direction) 
				? Direction.DESC : Direction.ASC;
		
		String choseField = service.validateField(field);
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, choseField));
		
		return ResponseEntity.ok(service.findAll(pageable));
	}
	@PostMapping(
			consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
			produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
			)
	@Operation(summary = "Create", description = "add a new book ", 
	tags = {"Books"}, 
	responses = {
		@ApiResponse(description = "Success", responseCode= "200", 
					content = 
						@Content(schema = @Schema(implementation = BookVO.class))
			),
		@ApiResponse(description = "Bad request", responseCode= "400", content = @Content),
		@ApiResponse(description = "Anauthorized", responseCode= "401", content = @Content),
		@ApiResponse(description = "Internal server error", responseCode= "500", content = @Content),
		})
	public BookVO create(@RequestBody BookVO book) {
		return service.create(book);
	}
	@PutMapping(
			consumes={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE },
			produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }
			)
	@Operation(summary = "FindAll", description = "find all book's ", 
	tags = {"Books"}, 
			responses = {
					@ApiResponse(description = "Success", responseCode= "200", 
							content = {
								@Content(schema = @Schema(implementation = BookVO.class)
										)
									}),
		@ApiResponse(description = "Bad request", responseCode= "400", content = @Content),
		@ApiResponse(description = "Anauthorized", responseCode= "401", content = @Content),
		@ApiResponse(description = "Not found", responseCode= "404", content = @Content),
		@ApiResponse(description = "Internal server error", responseCode= "500", content = @Content),
		})
	public BookVO update(@RequestBody BookVO book) {
		return service.update(book);
	}
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "delete", description = "delete a book", 
	tags = {"books"}, 
	responses = {
		@ApiResponse(description = "No content", responseCode= "204", 
					content = @Content
			),
		@ApiResponse(description = "Bad request", responseCode= "400", content = @Content),
		@ApiResponse(description = "Anauthorized", responseCode= "401", content = @Content),
		@ApiResponse(description = "Not found", responseCode= "404", content = @Content),
		@ApiResponse(description = "Internal server error", responseCode= "500", content = @Content),
		})
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
