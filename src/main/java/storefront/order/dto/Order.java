package storefront.order.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import storefront.user.dto.User;

@Data
@Builder
public class Order {
    @NotNull(message = "Order id can't be empty")
    private Integer id;
    @NotNull(message = "ProductId can't be empty")
    private Integer productId;
    @NotNull(message = "user id can't be empty")
    private Integer userId;

    @NotNull(message = "Shipping details can't be empty")
    @Valid
    private ShippingDetails shippingDetails;

    @Valid
    private User user;

    @Data
    @Builder
    public static class ShippingDetails {
        @NotBlank(message = "name can't be empty")
        @Pattern(regexp="^[a-zA-Z ]*$", message="Please provide a valid user name")
        private String name;
        @NotBlank(message = "Address can't be empty")
        private String address;
        @NotBlank(message = "Phone number can't be empty")
        @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Please provide a valid phone number")
        private String phone;
        @NotBlank(message = "email can't be empty")
        @Email(message="Please provide a valid email address")
        @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
        private String email;
        @NotBlank(message = "credit card number can't be empty")
        @Pattern(regexp="[0-9]+", message="Please valid credit card number")
        @Size(min = 19, max = 19, message="Please provide a credit card number")
        private String creditCardNumber;
    }
}
