package miu.edu.product;

import miu.edu.product.domain.Product;
import miu.edu.product.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }
    @Bean
    CommandLineRunner runner(ProductRepository productRepository) {
        return args -> {
            Product product = new Product();
            product.setProductnumber(1);
            product.setDescription("Iphone13");
            product.setName("Iphone13");
            productRepository.save(product);
//
        };
    }
}
