package br.com.erudio.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")

public class Book implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@Column(name= "author", nullable = false)
	private String author;
	
	@Column(name= "launch_date", nullable = false)
	private Date launchDate;
	
	@Column(name= "price", nullable = false)
	private double price;
	
	@Column(name= "title", nullable = false)
	private String title;

	public long getId() {
		return id;
	}
	
	public String getAuthor() {
		return author;
	}

	public Date getLunchDate() {
		return launchDate;
	}

	public double getPrice() {
		return price;
	}

	public String getTitle() {
		return title;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setLunchDate(Date launchDate) {
		this.launchDate = launchDate;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int hashCode() {
		return Objects.hash(author, id, launchDate, price, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(author, other.author) && id == other.id && Objects.equals(launchDate, other.launchDate)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
				&& Objects.equals(title, other.title);
	}

	
	
	
}
