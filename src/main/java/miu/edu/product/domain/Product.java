package miu.edu.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


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
	private String name;
	@NotEmpty(message = "{error.string.empty}")
	@Size(min=10, message = "{error.size.min}")
	@Column(columnDefinition = "TEXT")
	private String description;
	@NotNull(message = "{error.any.null}")
	private Double price;
	@NotEmpty(message = "{error.string.empty}")
	private String photo;
	private Date createdDate;

	@Transient
	private MultipartFile image;

	@ManyToOne //(cascade = CascadeType.ALL)
	@JoinColumn(name = "categoryId")
	@JsonIgnore
	private Category category;

	@ManyToOne
	@JoinColumn(name = "vendorId")
	private Vendor vendor;

	public Product(Integer productnumber,Double price,String description,Vendor vendor) {
		this.productnumber= productnumber;
		this.price = price;
		this.description =description;
		this.vendor = vendor;
	}
}
