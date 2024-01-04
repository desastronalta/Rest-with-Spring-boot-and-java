package br.com.erudio.data.vo.v1;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

public class BookVO extends RepresentationModel<BookVO> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String author;
	
	private Date launchDate;
	
	private double price;
	
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
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setLunchDate(Date lunchDate) {
		this.launchDate = lunchDate;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(author, id, launchDate, price, title);
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
		BookVO other = (BookVO) obj;
		return Objects.equals(author, other.author) && id == other.id && Objects.equals(launchDate, other.launchDate)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price)
				&& Objects.equals(title, other.title);
	}

	
	
}
