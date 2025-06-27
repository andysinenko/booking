package homes.sinenko.booking;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Booking Service API",
				description = "REST API for managing booking units, availability, and reservations.",
				version = "v1",
				contact = @Contact(
						name = "Sinenko Dev Team",
						email = "support@sinenko.homes",
						url = "https://sinenko.homes"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://sinenko.homes"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Full API Docs",
				url = "http://localhost:8080/swagger-ui.html"
		)
)
public class BookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingApplication.class, args);
	}

}
