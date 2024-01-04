package br.com.erudio.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.erudio.model.Person;

@Service
public class MyMapper {
	
	
	// auxilia na converção de objetos para VOs de maneira dinamica e eficiente.
	private static ModelMapper mapper = new ModelMapper();
	
	// utilizado para retornar um VO de forma genérica.
	public  <O , D> D parseObject(O origin, Class<D> destinaiton) {
		if(origin.getClass().equals(Person.class)) {
		}
		return mapper.map(origin, destinaiton);
	}
	// retonna uma Lista de VOs de forma genérica.
	public  <O , D> List<D> parseListObject(List<O> origin, Class<D> destinaiton) {
		List<D> destinationObjects = new ArrayList<D>();
		for(O o : origin) {
			destinationObjects.add(mapper.map(o, destinaiton));
		}
		return destinationObjects;
	}
}
