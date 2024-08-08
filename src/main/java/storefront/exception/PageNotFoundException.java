package storefront.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException(String message)
    {
        super(message);
    }
}
