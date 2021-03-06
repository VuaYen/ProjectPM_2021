package miu.edu.product.web;

import miu.edu.product.domain.*;
import miu.edu.product.dto.ReportRequestDTO;
import miu.edu.product.dto.ReportResponseDTO;
import miu.edu.product.repository.VendorRepository;
import miu.edu.product.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
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

    @Autowired
    ReportService reportService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderDetailService orderDetailService;

    @GetMapping("/report")
    public String generateDataReport(HttpServletRequest httpServletRequest,Model model) {
        ApiResponse<ReportResponseDTO> response = new ApiResponse<>();
        try {
            //setup parameters
            System.out.println("httpServletRequest " + httpServletRequest.toString());
            ReportRequestDTO reportRequestDTO = Converter.convert(httpServletRequest);
            System.out.println("ReportRequestDTO " + reportRequestDTO);
            //generate sale report
            ReportResponseDTO reportResponseDTO = reportService.generateReport(reportRequestDTO);

            response.setData(reportResponseDTO);
            response.setMessage("Successfully");
            //response.setStatus();
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response).toString();
        }

        model.addAttribute("reports", response.getData());
        return "admin/report";
    }

    @GetMapping("approve")
    public String productsregister(Model model, HttpServletRequest request){

        List<Product> productOptional = productService.findAllByStatus(ProductStatus.New);

        model.addAttribute("products", productOptional);

        return "admin/productsreg";
    }
    @PostMapping("product/active")
    public String productActive(@RequestParam(name = "id") Integer id){

        try {

            productService.activeProduct(id);
            return "redirect:/admin/approve";
        }
        catch(Exception ex){

        }
        return "/";

    }


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

    @GetMapping("orders")
    public String orders(Model model,  HttpServletRequest request){


        model.addAttribute("orders", orderService.getAll());
        return "/admin/orders";

    }

    @PostMapping("changeStatus")
    public String changeStatus(@RequestParam(name = "status") OrderStatus status, @RequestParam(name = "id") Integer id){

        orderService.updateStatus(status,id);
        return "redirect:/admin/orders";
    }

    @GetMapping("orderdetail")
    public String detail(@RequestParam(name="id") Integer id,Model model){

        System.out.println("id =" + id);

        model.addAttribute("productdetails", orderDetailService.getDetailByOrderId(id));
        return "/admin/detail";

    }
}
