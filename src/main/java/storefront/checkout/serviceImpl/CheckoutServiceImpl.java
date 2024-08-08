package storefront.checkout.serviceImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.NotFound;
import org.springframework.stereotype.Service;
import storefront.checkout.dto.Checkout;
import storefront.checkout.service.CheckoutService;
import storefront.mapper.CheckoutToOrderMapper;
import storefront.order.dto.Order;
import storefront.product.dto.Product;
import storefront.user.dto.User;
import storefront.user.serviceImpl.UserServiceImpl;
import storefront.validation.CheckoutValidation;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CheckoutServiceImpl implements CheckoutService {
    private final CheckoutToOrderMapper checkoutToOrderMapper;
    private final CheckoutValidation checkoutValidation;

    public CheckoutServiceImpl(CheckoutToOrderMapper checkoutToOrderMapper, CheckoutValidation checkoutValidation) {
        this.checkoutToOrderMapper = checkoutToOrderMapper;
        this.checkoutValidation = checkoutValidation;
    }

    @Override
    public int checkoutProduct(Checkout checkout) {
        checkoutValidation.validate(checkout);
        log.info("Successfully checked out the product: {}", checkout);
        //todo: Saving new order details into database
        return save(checkout);
    }

    @SneakyThrows
    public int save(Checkout checkout){
        Type listType = new TypeToken<List<Order>>() {}.getType();
        Reader reader = new InputStreamReader(Objects.requireNonNull(CheckoutServiceImpl.class
                .getResourceAsStream("/orders.json")));
        List<Order> orders = new Gson().fromJson(reader, listType);

        int orderId = orders.isEmpty()?1:orders.size()+1;
        Order newOrder = checkoutToOrderMapper.checkoutToOrderMap(orderId, checkout);
        orders.add(newOrder);

        URL resource = CheckoutServiceImpl.class.getResource("/orders.json");
        Writer writer = new FileWriter(resource.getPath());
        Gson gson = new GsonBuilder().create();
        gson.toJson(orders, writer);
        writer.flush();
        writer.close();

        log.info("Order placed successfully: {}", newOrder);
        return newOrder.getId();
    }
}
