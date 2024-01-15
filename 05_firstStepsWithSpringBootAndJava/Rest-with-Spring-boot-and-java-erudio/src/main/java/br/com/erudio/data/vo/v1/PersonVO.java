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
	
	private boolean enabled;
	
	public PersonVO() {
		
	}
	
	
	
	public boolean isEnabled() {
		return enabled;
	}



	public void setKey(Long key) {
		this.key = key;
	}



	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(adress, enabled, firstName, gender, key, lastName);
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonVO other = (PersonVO) obj;
		return Objects.equals(adress, other.adress) && enabled == other.enabled
				&& Objects.equals(firstName, other.firstName) && Objects.equals(gender, other.gender)
				&& Objects.equals(key, other.key) && Objects.equals(lastName, other.lastName);
	}
	
	
}
