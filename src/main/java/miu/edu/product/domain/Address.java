package miu.edu.product.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;


@Setter
@Getter
@Embeddable
public class Address {
	private String street;
	private String city;
	private String state;
	private String zip;
}
