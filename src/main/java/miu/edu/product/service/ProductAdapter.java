package miu.edu.product.service;


import miu.edu.product.domain.Product;
import miu.edu.product.domain.Vendor;

public class ProductAdapter {
	public static Product getProduct(ProductDTO productDTO) {
		Product product = new Product(
				productDTO.getProductnumber(),productDTO.getPrice(),
				productDTO.getDescription(),new Vendor()

				);		
		return product;				
	}
	
	public static ProductDTO getProductDTO(Product product) {
		ProductDTO productDTO = new ProductDTO(
				product.getProductnumber(),
				product.getDescription(),
				product.getPrice()
				);		
		return productDTO;				
	}
}
