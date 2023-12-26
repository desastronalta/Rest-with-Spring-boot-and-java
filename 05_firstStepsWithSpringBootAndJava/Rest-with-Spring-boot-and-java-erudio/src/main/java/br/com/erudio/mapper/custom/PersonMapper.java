package br.com.erudio.mapper.custom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.model.Person;

@Service
public class PersonMapper {
	public Person convertEntityToVo(PersonVO person) {
		
			Person entity = new Person();
			entity.setId(person.getKey());
			entity.setAdress(person.getAdress());
			entity.setFirstName(person.getFirstName());
			entity.setLastName(person.getLastName());
			entity.setGender(person.getGender());
			return entity;
	}
		public PersonVO convertEntityToVO(Person person) {
			PersonVO vo = new PersonVO();
			vo.setKey(person.getId());
			vo.setAdress(person.getAdress());
			vo.setFirstName(person.getFirstName());
			vo.setLastName(person.getLastName());
			vo.setGender(person.getGender());
			return vo;
		}
		public List<PersonVO> convertListEntityToVO(List<Person> persons){
			List<PersonVO> vOs = new ArrayList<>();
			for(Person p: persons) {
				vOs.add(convertEntityToVO(p));
			}
			return vOs;
		}
		public List<Person> convertListVoToEntity(List<PersonVO> vo){
			List<Person> pessoas = new ArrayList<>();
			for(PersonVO vos: vo) {
				pessoas.add(convertEntityToVo(vos));
			}
			return pessoas;
		}
	}

