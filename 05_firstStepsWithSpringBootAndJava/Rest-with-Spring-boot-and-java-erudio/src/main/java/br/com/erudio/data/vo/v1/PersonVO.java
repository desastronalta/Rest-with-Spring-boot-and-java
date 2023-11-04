package br.com.erudio.data.vo.v1;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/*
 * utilizado para passar a ordem em que os dados serão exibidos.
 */
@JsonPropertyOrder({"id", "firstName", "lastName",  "adress","gender"})
public class PersonVO implements Serializable{

	private static final long serialVersionUID = 1L;
	/*
	 * utilizado para ignorar um atributo na serialização.
	 */
	@JsonIgnore
	private Long id;
	/*
	 * utilizado para passar como sera apresentada a variavel.
	 */
	@JsonProperty("first_name")
	private String firstName;
	@JsonProperty("last_name")
	private String lastName;

	private String adress;

	private String gender;
	public PersonVO() {
		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
		return Objects.hash(adress, firstName, gender, id, lastName);
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
				&& Objects.equals(gender, other.gender) && id == other.id && Objects.equals(lastName, other.lastName);
	}
	
}
