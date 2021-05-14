package miu.edu.product.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;


@Setter
@Getter
@Embeddable
public class Address {
	private String street;
//	@JoinColumn(name="city",referencedColumnName="city", insertable = false, updatable = false)
	private String city;
	private String state;
	private String zip;

	@Override
	public String toString() {
		return "Address{" +
				"street='" + street + '\'' +
				", city='" + city + '\'' +
				", state='" + state + '\'' +
				", zip='" + zip + '\'' +
				'}';
	}
}
