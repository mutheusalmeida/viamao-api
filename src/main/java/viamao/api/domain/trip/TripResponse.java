package viamao.api.domain.trip;

import java.time.LocalDateTime;

public record TripResponse(
		
		String id,
		String title,
		String description,
		String destination,
		LocalDateTime startDate,
		LocalDateTime endDate
		
		) {

	public TripResponse(Trip trip, String id) {
		this(
				id, 
				trip.getTitle(),
				trip.getDescription(), 
				trip.getDestination(),
				trip.getStartDate(),
				trip.getEndDate());
	}
	
}
