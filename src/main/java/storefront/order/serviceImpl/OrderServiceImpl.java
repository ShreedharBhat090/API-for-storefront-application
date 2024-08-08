package storefront.order.serviceImpl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import storefront.order.dto.Order;
import storefront.order.service.OrderService;
import storefront.product.dto.Product;
import storefront.product.serviceImpl.ProductServiceImpl;
import storefront.user.dto.User;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public Order getOrder(int id) {
        //todo: write repository to get order
        return findById(id);
    }

    @Override
    public List<Order> getOrders() {
        //todo: write repository to get all orders
        return findAll();
    }

    @SneakyThrows
    public Order findById(int id) {
        Type listType = new TypeToken<List<Order>>() {}.getType();
        Reader reader =new InputStreamReader(Objects.requireNonNull(OrderServiceImpl.class
                .getResourceAsStream("/orders.json")));
        List<Order> orders = new Gson().fromJson(reader, listType);

        orders = addUserDetails(orders);

        return orders.stream().filter(o -> o.getId()==id)
                .peek(a -> log.info("Successfully found the order: {} of the user: {}", a.getUserId(), a.getId()))
                .findFirst().orElseThrow(() ->
                new IllegalArgumentException("No order found with the  order id: %d".formatted(id)));
    }

    @SneakyThrows
    public List<Order> findAll() {
        Type listType = new TypeToken<List<Order>>() {}.getType();
        Reader reader =new InputStreamReader(Objects.requireNonNull(OrderServiceImpl.class
                .getResourceAsStream("/orders.json")));
        List<Order> orders = new Gson().fromJson(reader, listType);

        orders = addUserDetails(orders);

        log.info("Successfully returning order list");
        return orders;
    }

    public List<Order> addUserDetails(List<Order> orders){
        Type listType1 = new TypeToken<List<User>>() {}.getType();
        Reader reader1 =new InputStreamReader(Objects.requireNonNull(OrderServiceImpl.class
                .getResourceAsStream("/users.json")));
        List<User> users = new Gson().fromJson(reader1, listType1);

        orders.forEach(o-> o.setUser(users.stream().filter(u->u.getId()==o.getUserId()).findFirst().get()));

        return orders;
    }
}
