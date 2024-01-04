package br.com.erudio.data.vo.v1;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;




/*
 * utilizado para passar a ordem em que os dados serão exibidos.
 */
@JsonPropertyOrder({"key", "firstName", "lastName",  "adress","gender"})
public class PersonVO extends RepresentationModel<PersonVO> implements Serializable {

	private static final long serialVersionUID = 1L;
	/*
	 * utilizado para ignorar um atributo na serialização.
	 */
	@JsonProperty("id")
	private Long key;
	/*
	 * utilizado para passar como sera apresentada a variavel.
	 */
	
	private String firstName;

	private String lastName;

	private String adress;

	private String gender;
	public PersonVO() {
		
	}
	
	public long getKey() {
		return key;
	}

	public void setKey(long id) {
		this.key = id;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(adress, firstName, gender, key, lastName);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonVO other = (PersonVO) obj;
		return Objects.equals(adress, other.adress) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(gender, other.gender) && key == other.key && Objects.equals(lastName, other.lastName);
	}
	
}
