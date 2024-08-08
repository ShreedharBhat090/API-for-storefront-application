package storefront.product.serviceImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import storefront.exception.PageNotFoundException;
import storefront.product.dto.Product;
import storefront.product.service.ProductService;
import storefront.user.serviceImpl.UserServiceImpl;

import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Override
    public Page<Product> getProductsByPagination(Integer pageNo, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        //do findAll from repository
        return findAll(pageRequest);
    }

    @Override
    public Integer createProduct(Product product) {
        //save to repository
        return save(product);
    }

    @SneakyThrows
    public int save(Product product){
        Type listType = new TypeToken<List<Product>>() {}.getType();
        Reader reader =new InputStreamReader(Objects.requireNonNull(ProductServiceImpl.class
                .getResourceAsStream("/products.json")));
        List<Product> products = new Gson().fromJson(reader, listType);

        product.setId(products.isEmpty() ? 1 : products.size()+1);
        products.add(product);

        URL resource = ProductServiceImpl.class.getResource("/products.json");
        Writer writer = new FileWriter(resource.getPath());
        Gson gson = new GsonBuilder().create();
        gson.toJson(products, writer);
        writer.flush();
        writer.close();

        log.info("New product added successfully. {}", product);
        return product.getId();
    }

    @SneakyThrows
    public  Page<Product> findAll(PageRequest pageRequest) {
        Type listType = new TypeToken<List<Product>>() {}.getType();
        Reader reader =new InputStreamReader(Objects.requireNonNull(UserServiceImpl.class
                .getResourceAsStream("/products.json")));
        List<Product> products = new Gson().fromJson(reader, listType);

        Pageable pageable = PageRequest.of(pageRequest.getPageNumber(),pageRequest.getPageSize());
        int lowerBound = pageable.getPageNumber() * pageable.getPageSize();
        int upperBound = Math.min(lowerBound + pageable.getPageSize() , products.size());
        if(lowerBound>upperBound){
            throw new PageNotFoundException("Page not found");
        }
        List<Product> subList = products.subList(lowerBound, upperBound);

        log.info("Successfully returning product list");
        return new PageImpl<>(subList,pageable,products.size());
    }
}
