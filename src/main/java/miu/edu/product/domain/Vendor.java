package miu.edu.product.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "vendor")
public class Vendor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	private String gender;
	@Email
	@NotNull
	@Column(nullable = false,unique = true)
	private String email;
	@NotNull
	@Column(unique = true)
	private String userName;

	private VendorStatus status;

	@Embedded
	private Address address;

//	@JsonIgnore
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "vendor")
//	private List<Product> products;


}
