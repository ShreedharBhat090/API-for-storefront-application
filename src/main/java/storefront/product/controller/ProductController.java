package storefront.product.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import storefront.product.dto.Product;
import storefront.product.service.ProductService;
import storefront.user.dto.User;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Page<Product> getProducts(@RequestParam(defaultValue = "0") Integer pageNo,
                                     @RequestParam(defaultValue = "5") Integer pageSize){
        return productService.getProductsByPagination(pageNo,pageSize);
    }

    @PostMapping
    public ResponseEntity<Integer> createProduct(@Valid @RequestBody Product product) {
        return new ResponseEntity<>(productService.createProduct(product), HttpStatus.OK);
    }
}
