package miu.edu.product.web;

import miu.edu.product.domain.Product;
import miu.edu.product.domain.Vendor;
import miu.edu.product.repository.VendorRepository;
import miu.edu.product.service.ProductCatalogService;
import miu.edu.product.service.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4201")
public class ProductController {
	@Autowired
	ProductCatalogService productCatalogService;
	@Autowired
	VendorRepository vendorRepository;
	@Autowired
	ProductServiceImp productService;

	@GetMapping("/api/product")
	public List<Product> getAllProduct() {

		return productCatalogService.getAll();
	}
	@PostMapping("/api/product")
	void addPorduct(@RequestBody Product product){
		productService.save(product);
	}

//	@GetMapping("/api/product/{productnumber}")
//	public ResponseEntity<?> getContact(@PathVariable Integer productnumber) {
//		ProductDTO productDTO = productCatalogService.getProduct(productnumber);
//		return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);
//	}
	@GetMapping("/api/product/{productnumber}")
	public Product getProduct(@PathVariable Integer productnumber) {
		return productService.getById(productnumber).get();
	}

	@PostMapping(value = "/api/product/{name}/{description}/{price}/{photo}/{catid}/{venid}")
	public Product addProduct(@PathVariable String name,@PathVariable String description, @PathVariable double price
			,@PathVariable String photo,@PathVariable Integer catid,@PathVariable Integer venid) {
		Product product= productCatalogService.addProduct(name, description,price,photo,catid,venid );
		System.out.println(product);
//		return new ResponseEntity<Product>(HttpStatus.OK);
		return product;
	}
	@GetMapping("/api/vendors")
	public List<Vendor> getVendors(){
		return (List<Vendor>)vendorRepository.findAll();
	}

	@PostMapping("/api/vendors")
	void addVendor(@RequestBody Vendor vendor){
		vendorRepository.save(vendor);
	}


}
