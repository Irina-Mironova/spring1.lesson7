package ru.geekbrains.spring1.lesson7.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring1.lesson7.dtos.ProductDto;
import ru.geekbrains.spring1.lesson7.services.ProductService;


@Controller
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/products")
    public String showProducts(Model model, @RequestParam(defaultValue = "1") int pageIndex) {
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        Page<ProductDto> productPage = productService.showProducts(pageIndex - 1, 5).map(ProductDto::new);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("totalProducts", productPage.getTotalElements());
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("currentPage", pageIndex);
        return "productsAll";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id, @RequestParam int pageIndex) {
        productService.deleteProduct(id);
        return "redirect:/products?pageIndex=" + pageIndex;
    }
}
