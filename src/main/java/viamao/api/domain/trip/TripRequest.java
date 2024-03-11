package viamao.api.domain.trip;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TripRequest(
		
		@NotBlank(message = "Title is required")
		String title,
		
		@NotBlank(message = "Description is required")
		String description,
		
		@NotBlank(message = "Destination is required")
		String destination,
		
		@NotNull(message = "Start date is required")
		LocalDate startDate,
		
		@NotNull(message = "End date is required")
		LocalDate endDate
		
		) {

}
