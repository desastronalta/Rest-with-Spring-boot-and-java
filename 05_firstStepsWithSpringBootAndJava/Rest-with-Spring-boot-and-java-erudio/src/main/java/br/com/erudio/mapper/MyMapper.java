package br.com.erudio.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;




public class MyMapper {
	// auxilia na converção de objetos para VOs de maneira dinamica e eficiente.
	private static ModelMapper mapper = new ModelMapper();
	
	// utilizado para retornar um VO de forma genérica.
	public static  <O , D> D parseObject(O origin, Class<D> destinaiton) {
		return mapper.map(origin, destinaiton);
	}
	// retonna uma Lista de VOs de forma genérica.
	public static  <O , D> List<D> parseListObject(List<O> origin, Class<D> destinaiton) {
		List<D> destinationObjects = new ArrayList<D>();
		for(O o : origin) {
			destinationObjects.add(mapper.map(o, destinaiton));
		}
		return destinationObjects;
	}
}
