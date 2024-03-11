package viamao.api.domain.trip;

import java.time.LocalDateTime;

public record UpdateTripRequest(
		
		String title,
		String description,
		String destination,
		LocalDateTime startDate,
		LocalDateTime endDate
		
		) {
	
}
