package storefront.user.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import storefront.checkout.dto.Checkout;
import storefront.product.dto.Product;
import storefront.user.dto.User;
import storefront.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<User> getUsers(@RequestParam(defaultValue = "0") Integer pageNo,
                                     @RequestParam(defaultValue = "5") Integer pageSize){
        return userService.getUsers(pageNo,pageSize);
    }

    @PostMapping
    public ResponseEntity<Integer> createUser(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.OK);
    }
}
