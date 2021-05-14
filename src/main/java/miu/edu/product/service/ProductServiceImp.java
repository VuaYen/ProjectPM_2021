package miu.edu.product.service;


import miu.edu.product.domain.Category;
import miu.edu.product.domain.Product;
import miu.edu.product.domain.ProductStatus;
import miu.edu.product.domain.Vendor;
import miu.edu.product.repository.ProductRepository;
import miu.edu.product.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class ProductServiceImp implements ProductService {
 @Autowired
    private ProductRepository productRepository;


@Autowired
    private CategoryService categoryService;
    @Autowired
    private VendorRepository vendorService;


    @Override
    public List<Product> all() {
        return toList(productRepository.findAll());
    }

    @Override
    public Optional<Product> getById(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    public Page<Product> paging(String username , Pageable pageable) {

        return null;
    }

    @Override
    public Product save(Product product) {

        product.setStatus(ProductStatus.New);

        product.setCreatedDate(new Date());

        product.setQuantity(0);

        Vendor ventmp= new Vendor();
        ventmp.setId(5);
//        ventmp=vendorService.findById(5).get();
        Category cattmp = new Category();
        cattmp= categoryService.getById(1).get();
        if (product.getVendor()==null){
            product.setVendor(ventmp);
            System.out.println(" vender null"+ventmp.getUserName());
        }
        if (product.getCategory()==null){
            product.setCategory(cattmp);
            System.out.println(" category null"+cattmp.getName());
        }
        if (product.getPhoto()==null){
            product.setPhoto("http://localhost:8080/assets/images/demos/demo-4/products/product-16.jpg");

        }

        return productRepository.save(product);
    }

    @Override
    public void delete(Integer id) {
        //Because there is not casecase orderdetail in product side, if we want to delete product and
        //also delete ordertail. We need to delete orderdetail firstly. If this case cenarious, the quirerement is that
        //we don't allow delete product if product has orderdetail, so we commant the code line below.
        //orderDetailRepository.deleteByProductId(product.getId());

        productRepository.deleteById(id);
//        productRepository.deleteProductAndDeleteOrderDetailWihoutCastcase(id);

    }

    @Override
    public List<Product> getTopProducts() {
        return toList(productRepository.findAll());
//        return toList(productRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate")));//getTopProducts());
    }



    @Override
    public List<Product> getByCategory(Integer categoryid) {
        return toList(productRepository.getByCategory( categoryid));
    }


    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void activeProduct(Integer productnumber) {
        Optional<Product> product = getById(productnumber);
        Product product1= new Product();
        if (product.isPresent()){
            product1 =product.get();
            product1.setStatus(ProductStatus.Active);
            productRepository.save(product1);
        }else {
            System.out.println("There no product on this productnumber");
        }

    }

    @Override
    public List<Product> findAllByStatus(ProductStatus status) {
        return productRepository.findAllByStatus((status));
    }

    public static <T> List<T> toList(final Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}
