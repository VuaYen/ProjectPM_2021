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
//	@NotEmpty(message = "{error.string.empty}")
	private Integer productnumber;
	private String name;
	@NotEmpty(message = "{error.string.empty}")
	@Size(min=10, message = "{error.size.min}")
	@Column(columnDefinition = "TEXT")
	private String description;
	@NotNull(message = "{error.any.null}")
	private Double price;
//	@NotEmpty(message = "{error.string.empty}")
	private String photo;
	private Date createdDate;
	private ProductStatus status;

	@Transient
	private MultipartFile image;

	@ManyToOne //(cascade = CascadeType.ALL)
	@JoinColumn(name = "categoryId")
	@JsonIgnore
	private Category category;

	@ManyToOne
	@JoinColumn(name = "vendorId")
	private Vendor vendor;

	private Integer quantity;

	public Product(String name,String description,Double price,String photo,Category category,Vendor vendor) {
		this.name = name;
		this.photo =photo;
		this.price = price;
		this.createdDate = new Date();
		this.description =description;
		this.category = category;
		this.vendor = vendor;
		this.quantity =0;
		this.status = ProductStatus.New;
		this.image = null;
	}

	@Override
	public String toString() {
		return "Product{" +
				"productnumber=" + productnumber +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", price=" + price +
				", photo='" + photo + '\'' +
				", createdDate=" + createdDate +
				", status=" + status +
				", image=" + image +
				", category=" + category +
				", vendor=" + vendor +
				", quantity=" + quantity +
				'}';
	}
}
