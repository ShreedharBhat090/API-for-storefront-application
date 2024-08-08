package storefront.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class User {
    private int id;
    @NotBlank(message = "name can't be empty")
    @Pattern(regexp="^[a-zA-Z ]*$", message="Please provide a valid user name")
    private String name;
    @NotBlank(message = "Phone number can't be empty")
    @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Please provide a valid phone number")
    private String phone;
    @NotBlank(message = "email can't be empty")
    @Email(message="Please provide a valid email address")
    @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
    private String email;
    @NotBlank(message = "address can't be empty")
    private String address;
}
