package miu.edu.product.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotEmpty(message = "{error.string.empty}")
	private Integer productnumber;
	double price;
	String description;
	@ManyToOne
	@JoinColumn(name = "vendorId")
	private Vendor vendor;


	

}
