package pj.tpog11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pj.tpog11.repositories.ProductRepository;

@Controller
public class ProductController {

    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @RequestMapping("/products")
    public String getSubjects(Model model){
        model.addAttribute("products", productRepository.findAll());
        return "products";
    }
    @GetMapping("/product/{id}")
    public ModelAndView getProduct(@PathVariable Long id){
        ModelAndView mav = new ModelAndView("product");
        productRepository.findById(id).ifPresent(product -> mav.addObject("product", product));
        return mav;
    }
    @RequestMapping("/")
    public String getHome(Model model){
        model.addAttribute("products", productRepository.findAll());
        return "home";
    }

}
