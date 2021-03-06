package miu.edu.product.web;

import miu.edu.product.domain.Content;
import miu.edu.product.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.Valid;
import java.util.List;

@SessionAttributes("myCart")
@Controller
@RequestMapping(value = "/administration/cms-managent")
public class ContentController {
    @Autowired
    private MessageSource messageSource;

    @Autowired
    WebApplicationContext servletContext;
    @Autowired
    ContentService contentService;

    @GetMapping(value = {"","/"})
    public String index(Model model){
        //Content content = new Content("aboutus","about us","Content Here");
        //contentService.save(content);
        List<Content> contents = contentService.getAllContents();
        model.addAttribute("contents",contents);
        System.out.println("Here Bro");
        return "buyer/cms_list";
    }
    @GetMapping("/add")
    public String getContent(@ModelAttribute("content")Content content){
        return "buyer/contentForm";
    }
    @PostMapping("/add")
    public String addContent(@Valid @ModelAttribute("content")Content content, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("content",content);
            return "buyer/contentForm";
        }
        else{
            contentService.save(content);

            List<Content> contents = contentService.getAllContents();
            model.addAttribute("contents",contents);
            return "buyer/cms_list";
        }

    }

    @GetMapping(value = "/edit/{slug}")
    public String editContent(@Valid @ModelAttribute("content")Content content, BindingResult bindingResult, @PathVariable("slug")String slug, Model model){
        model.addAttribute("content",content);
        Content a= contentService.find(slug);
        model.addAttribute("content",a);
        return "buyer/editContentForm";
    }
    @GetMapping(value = "/delete/{slug}")
    public String deleteContent(@PathVariable("slug")String slug, Model model){
        contentService.delete(slug);
        List<Content> contents = contentService.getAllContents();
        model.addAttribute("contents",contents);
        return "buyer/cms_list";
    }

}
