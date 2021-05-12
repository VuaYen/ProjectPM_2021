package miu.edu.product.web;


import miu.edu.product.domain.Product;
import miu.edu.product.dto.Cart;
import miu.edu.product.service.CategoryService;
import miu.edu.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Controller
@SessionAttributes("myCart")
public class IndexController {

    private final ServletContext context;
    private ProductService productService;
    private CategoryService categoryService;


    @Autowired
    public IndexController(ServletContext context, ProductService productService, CategoryService categoryService) {
        this.context = context;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @ModelAttribute("myCart")
    public Cart getMyCart() {
        return new Cart();
    }


    @GetMapping("/")
    public String index(Model model, HttpServletRequest request) {
//        Principal user = request.getUserPrincipal();
//        String username = "";

        List<Product> listtop = productService.getTopProducts();
        List<Product> productListFollow = listtop;//new ArrayList<>();
        if (listtop.size() >= 4)
            productListFollow = listtop.subList(0, 4);
        if (listtop.size() > 7)
            listtop = listtop.subList(1, 9);

        model.addAttribute("productlistFlow", productListFollow);
        model.addAttribute("productlistTop", listtop);
        model.addAttribute("cats", categoryService.getAll());

        return "buyer/home";
    }

    @GetMapping("/products")
    public String products(Model model, @RequestParam(name = "type", required = false) String type, HttpServletRequest request) {
//        System.out.println(type);
//        Principal user = request.getUserPrincipal();
        String type1 = type;
        if (type1 == null)
            type1 = " ";
        String cat = request.getParameter("cat");
        List<Product> list = new ArrayList<>();
        String typep = "yes";
        String follow = "follow";
        String title = "Product List";
        String pcur = request.getParameter("pcur");

        if (cat != null) {
            list = productService.getByCategory(Integer.parseInt(cat));
            title = title + " of " + list.get(0).getCategory().getName();
        } else {
            list = productService.all();
        }
        int curp = 0;
        if (pcur != null) {
            curp = Integer.parseInt(pcur) - 1;
        }
        int page = list.size() / 8;
        int start = curp * 8, end = curp * 8 + 8;
        list = list.subList(start, end);

        model.addAttribute("typep", typep);
        model.addAttribute("follow", follow);
        model.addAttribute("title", title);
        model.addAttribute("productlist", list);
        model.addAttribute("page", page);
        model.addAttribute("cat", cat);
        return "buyer/products";
//        return "buyer/products-test";
    }

    @GetMapping("/product")
    public String product(Model model, HttpServletRequest request) {
        String proid = request.getParameter("pid");
        if (proid == null) {
            proid = "9";
        }
        Product pro = productService.getById(Integer.parseInt(proid)).get();
        model.addAttribute("pro", pro);
        return "buyer/product";
    }

    @GetMapping("/user")
    public String user() {
        return "buyer/user";
    }

    @GetMapping("/access-denied")
    public String denyAccess() {
        return "/access-denied";
    }

    @GetMapping("/about")
    public String showAbout() {
        return "buyer/about";
    }

    @GetMapping("/contact")
    public String showContact() {
        return "buyer/contact";
    }

}
