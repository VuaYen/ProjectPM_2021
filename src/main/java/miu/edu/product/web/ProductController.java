package miu.edu.product.web;

import miu.edu.product.domain.Product;
import miu.edu.product.domain.Vendor;
import miu.edu.product.service.ProductCatalogService;
import miu.edu.product.service.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
	@Autowired
	ProductCatalogService productCatalogService;

	@GetMapping("/product/{productnumber}")
	public ResponseEntity<?> getContact(@PathVariable Integer productnumber) {
		ProductDTO productDTO = productCatalogService.getProduct(productnumber);
		return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/product/{productnumber}/{price}/{description}/{vendor}")
	public ResponseEntity<?> addProduct(@PathVariable Integer productnumber, @PathVariable double price
			,@PathVariable String description,@PathVariable Vendor vendor) {
		productCatalogService.addProduct(productnumber, description,price,vendor );
		return new ResponseEntity<Product>(HttpStatus.OK);
	}


}
