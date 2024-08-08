package storefront.user.service;

import org.springframework.data.domain.Page;
import storefront.checkout.dto.Checkout;
import storefront.product.dto.Product;
import storefront.user.dto.User;

public interface UserService {
    Page<User> getUsers(Integer pageNo, Integer pageSize);

    Integer createUser(User user);
}
