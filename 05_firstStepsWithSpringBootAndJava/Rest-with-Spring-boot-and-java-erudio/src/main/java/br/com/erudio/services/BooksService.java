package br.com.erudio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
	MyMapper mapa;
	
	public List<BookVO> findAll(){
		var vo = mapa.parseListObject(repository.findAll(),BookVO.class);
		vo.stream().forEach(p-> p.add(linkTo(methodOn(BooksController.class).findById(p.getId())).withSelfRel()));
		return vo; 
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
}
