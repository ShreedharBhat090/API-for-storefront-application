package storefront.product.service;

import org.springframework.data.domain.Page;
import storefront.product.dto.Product;

import java.util.List;

public interface ProductService {
    Page<Product> getProductsByPagination(Integer pageNo, Integer pageSize);

    Integer createProduct(Product product);
}
