package viamao.api.domain.trip;

import java.time.LocalDateTime;

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
		LocalDateTime startDate,
		
		@NotNull(message = "End date is required")
		LocalDateTime endDate
		
		) {

}
