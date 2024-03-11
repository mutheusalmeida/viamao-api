package viamao.api.domain.trip;

import java.time.LocalDate;

public record TripResponse(
		
		String id,
		String title,
		String description,
		String destination,
		LocalDate startDate,
		LocalDate endDate
		
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
