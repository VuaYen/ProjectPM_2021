package miu.edu.product.service;





public class ProductDTO {

	Integer productnumber;
	double price;
	String description;

	public ProductDTO(Integer productnumber, String description, double price) {
		super();
		this.productnumber = productnumber;
		this.price = price;
		this.description = description;
	}
	
	public ProductDTO() {

	}

	public void print() {
		System.out.println(productnumber+" : "+description+" : "+price);
	}
	public Integer getProductnumber() {
		return productnumber;
	}

	public void setProductnumber(Integer productnumber) {
		this.productnumber = productnumber;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
