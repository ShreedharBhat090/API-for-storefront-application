package storefront.mapper;

import org.springframework.stereotype.Component;
import storefront.checkout.dto.Checkout;
import storefront.order.dto.Order;

@Component
public class CheckoutToOrderMapper {
    public Order checkoutToOrderMap(int id, Checkout checkout){
        return Order.builder().id(id).productId(checkout.getProductId())
                .shippingDetails(checkoutToOrderShippingDetailsMap(checkout.getShippingDetails()))
                .userId(checkout.getUserId()).build();
    }

    public Order.ShippingDetails checkoutToOrderShippingDetailsMap(Checkout.ShippingDetails checkoutShippingDetails){
        return Order.ShippingDetails.builder().name(checkoutShippingDetails.getName())
                .email(checkoutShippingDetails.getEmail())
                .phone(checkoutShippingDetails.getPhone())
                .address(checkoutShippingDetails.getAddress())
                .creditCardNumber(checkoutShippingDetails.getCreditCardNumber()).build();
    }
}
