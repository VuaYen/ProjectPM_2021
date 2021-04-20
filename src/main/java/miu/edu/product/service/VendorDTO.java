package miu.edu.product.service;


public class VendorDTO {
	int quantity;
	String locationcode;

	public VendorDTO(int quantity, String locationcode) {
		super();
		this.quantity = quantity;
		this.locationcode = locationcode;
	}

	public VendorDTO() {
		super();
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getLocationcode() {
		return locationcode;
	}

	public void setLocationcode(String locationcode) {
		this.locationcode = locationcode;
	}

	public void print() {
		System.out.println("Stock quantity: "+quantity+" , location code : "+locationcode);		
	}

}
