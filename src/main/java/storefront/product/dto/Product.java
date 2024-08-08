package storefront.product.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class Product {
    private int id;
    @NotBlank(message = "url can't be empty")
    private String url;
    @NotBlank(message = "Product name can't be empty")
    @Pattern(regexp="^[a-zA-Z ]*$", message="Please provide a valid user name")
    private String name;
    @NotBlank(message = "description can't be empty")
    private String description;
    @NotNull(message = "discount can't be empty")
    @Min(value=1, message="Please enter a valid discount percentage")
    @Max(value=100, message="Please enter a valid discount percentage")
    private Integer discount;
}
