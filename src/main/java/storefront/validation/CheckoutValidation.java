package storefront.validation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import storefront.checkout.dto.Checkout;
import storefront.checkout.serviceImpl.CheckoutServiceImpl;
import storefront.product.dto.Product;
import storefront.user.dto.User;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class CheckoutValidation {
    public void validate(Checkout checkout){
        Type listType = new TypeToken<List<Product>>() {}.getType();
        Reader reader =new InputStreamReader(Objects.requireNonNull(CheckoutServiceImpl.class
                .getResourceAsStream("/products.json")));
        List<Product> products = new Gson().fromJson(reader, listType);
        products.stream().filter(p -> p.getId()==checkout.getProductId()).findFirst().orElseThrow(() -> {
            log.warn("No product found with the  product id: {}", checkout.getProductId());
            return new IllegalArgumentException(MessageFormat.format(
                    "No product found with the  product id: {0}", checkout.getProductId()));
        });

        Type listType2 = new TypeToken<List<User>>() {}.getType();
        Reader reader2 =new InputStreamReader(Objects.requireNonNull(CheckoutServiceImpl.class
                .getResourceAsStream("/users.json")));
        List<User> users = new Gson().fromJson(reader2, listType2);
        users.stream().filter(u -> u.getId()==checkout.getUserId()).findFirst().orElseThrow(() -> {
            log.warn("No user found with the  user id: {}", checkout.getUserId());
            return new IllegalArgumentException("No user found with the  user id: %d".formatted(checkout.getUserId()));
        });
    }
}
