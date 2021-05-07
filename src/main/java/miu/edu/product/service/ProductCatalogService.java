package miu.edu.product.service;



import miu.edu.product.domain.Category;
import miu.edu.product.domain.Product;
import miu.edu.product.domain.Vendor;
import miu.edu.product.repository.CategoryRepository;
import miu.edu.product.repository.ProductRepository;
import miu.edu.product.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductCatalogService {
	@Autowired
	ProductRepository productRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	VendorRepository vendorRepository;

	public List<Product> getAll(){
		return productRepository.findAll();

	}

	public Product addProduct(String name, String description, double price,String photo,Integer catid,Integer venid) {
		Category category =categoryRepository.findById(catid).get();
		Vendor vendor = vendorRepository.findById(venid).get();
		Product product = new Product(name,description, price,photo,category,vendor);
		if (vendor==null)
			System.out.println("vendor null");
		System.out.println(vendor.getUserName());
		System.out.println(product);
		productRepository.save(product);
		return product;
		
	}
	public ProductDTO getProduct(Integer productnumber) {
		Optional<Product> result = productRepository.findById(productnumber);
		if (result.isPresent())
		  return ProductAdapter.getProductDTO(result.get());
		else
			return null;
	}

		

}
