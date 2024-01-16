package br.com.erudio.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import br.com.erudio.controllers.BooksController;
import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.exceptions.ResourceNotFoundEXception;
import br.com.erudio.mapper.MyMapper;
import br.com.erudio.model.Book;
import br.com.erudio.repositories.BooksRepository;

@Service
public class BooksService {

	@Autowired
	BooksRepository repository;
	
	@Autowired
	PagedResourcesAssembler<BookVO> assembler;
	
	@Autowired
	MyMapper mapa;
	
	public PagedModel<EntityModel<BookVO>> findAll(Pageable pageable){	
		
		var bookPage = repository.findAll(pageable);
		var bookVosPage = bookPage.map(p -> mapa.parseObject(p, BookVO.class));
		
		bookVosPage.map(p -> p.add(linkTo(methodOn(BooksController.class).findById(p.getId())).withSelfRel()));
		
		Link link = linkTo(methodOn(BooksController.class)
				.findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc", "author")).withSelfRel();
		return assembler.toModel(bookVosPage, link);
	}
	
	public BookVO findById(Long name) {
		var vo = mapa.parseObject(repository.findById(name),BookVO.class );
		vo.add(linkTo(methodOn(BooksController.class).findById(vo.getId())).withSelfRel());
		return vo;
	}
	
	public BookVO create(BookVO book) {
		var entity = mapa.parseObject(book, Book.class);
		var vo = mapa.parseObject(repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BooksController.class).findById(vo.getId())).withSelfRel());
		return vo;
	}
	
	public BookVO update(BookVO book) {
		var entity = repository.findById(book.getId())
				.orElseThrow(() -> new ResourceNotFoundEXception("No records found for this name."));
		entity.setAuthor(book.getAuthor());
		entity.setLunchDate(book.getLunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());
		var vo = mapa.parseObject(repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BooksController.class).findById(vo.getId())).withSelfRel());
		return vo;
	}
	
	public void delete(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundEXception("No records found for this name."));
		repository.delete(entity);
	}

	public String validateField(String field) {
		
		if(field.equals("author") || field.equals("title") || field.equals("launchDate") || field.equals("price")) {
			return field;
		}else return "author";
	
	}
}
