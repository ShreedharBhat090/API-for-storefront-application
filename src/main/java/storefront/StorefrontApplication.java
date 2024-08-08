package storefront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class StorefrontApplication {

	public static void main(String[] args) {
		SpringApplication.run(StorefrontApplication.class, args);
	}

}
