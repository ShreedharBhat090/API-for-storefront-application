package storefront.checkout.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

import java.io.Serializable;

@Data
public class Checkout {
    @NotNull(message = "ProductId can't be empty")
    private Integer productId;
    @NotNull(message = "ProductId can't be empty")
    private Integer userId;

    @NotNull(message = "Shipping details can't be empty")
    @Valid
    private ShippingDetails shippingDetails;

    @Data
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
