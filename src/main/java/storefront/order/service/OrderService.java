package storefront.order.service;

import storefront.order.dto.Order;

import java.util.List;

public interface OrderService {
    Order getOrder(int id);

    List<Order> getOrders();
}
