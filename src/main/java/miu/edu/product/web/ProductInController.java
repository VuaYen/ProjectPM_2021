package miu.edu.product.web;

import miu.edu.product.domain.Product;
import miu.edu.product.domain.Vendor;
import miu.edu.product.repository.VendorRepository;
import miu.edu.product.service.CategoryService;
import miu.edu.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/")
@SessionAttributes({ "product", "categories" })
public class ProductInController {
    @Autowired
    ProductService productService;

    @Autowired
    ServletContext context;

    @Autowired
    VendorRepository accountService;

    @Autowired
    CategoryService categoryService;


    @GetMapping("product")
    public String init(@RequestParam(name = "productnumber", required = false) Integer productnumber, Model model){

        if(productnumber==null) { model.addAttribute("product", new Product());}
        else  model.addAttribute("product", productService.getById(productnumber).get());

        model.addAttribute("categories", categoryService.getAll());

        return "admin/product";
    }

    @GetMapping("products")
    public String products(Model model, HttpServletRequest request){

        List<Product> productOptional = productService.all();

        model.addAttribute("products", productOptional);

        return "admin/products";
    }


    @PostMapping("product")
    public String save(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult,
                       SessionStatus status, HttpServletRequest request){


        Vendor vendor= new Vendor();
        vendor.setId(5);
        product.setVendor(vendor);
        productService.save(product);
        status.setComplete();
        return "redirect:/admin/products";
    }


    @PostMapping("product/delete")
    public String delete(@RequestParam(name = "id") Integer id){

        try {

            productService.delete(id);
            return "redirect:/admin/products";
        }
        catch(Exception ex){

        }
        return "/";

    }
}
