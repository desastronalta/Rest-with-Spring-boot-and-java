package br.com.erudio.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/*
 * através dessa notation dizemos para o hibernate que essa classe é uma tabela do banco de dados
 */
@Entity
@Table(name = "person")
/*
 * a notion table é utilizada para passar para o hibernate que no banco essa
 *  classe possui outro nome
 */


public class Person implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(name = "first_name", nullable = false, length = 80)
	private String firstName;
	
	@Column(name = "last_name", nullable = false, length = 80)
	private String lastName;
	
	@Column(nullable = false, length = 100)
	private String adress;
	
	@Column(nullable = false, length = 6)
	private String gender;
	
	@Column(nullable = false)
	private boolean enabled;
	public Person() {
		
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
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public int hashCode() {
		return Objects.hash(adress, enabled, firstName, gender, id, lastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		return Objects.equals(adress, other.adress) && enabled == other.enabled
				&& Objects.equals(firstName, other.firstName) && Objects.equals(gender, other.gender)
				&& Objects.equals(id, other.id) && Objects.equals(lastName, other.lastName);
	}

}
