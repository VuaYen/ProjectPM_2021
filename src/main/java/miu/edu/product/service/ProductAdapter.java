package miu.edu.product.service;


import miu.edu.product.domain.Product;

public class ProductAdapter {
	public static Product getProduct(ProductDTO productDTO) {
		Product product = new Product();
		product.setProductnumber(productDTO.getProductnumber());
		product.setPrice(productDTO.getPrice());
		product.setDescription(productDTO.getDescription());

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
