package miu.edu.product.service;


import miu.edu.product.domain.Product;
import miu.edu.product.domain.Vendor;
import miu.edu.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductCatalogService {
	@Autowired
	ProductRepository productRepository;

	public void addProduct(Integer productnumber, String description, double price,Vendor vendor) {

		Product product = new Product(productnumber, price,description,  vendor);
		productRepository.save(product);
		
	}
	public ProductDTO getProduct(Integer productnumber) {
		Optional<Product> result = productRepository.findById(productnumber);
		if (result.isPresent())
		  return ProductAdapter.getProductDTO(result.get());
		else
			return null;
	}

		

}
