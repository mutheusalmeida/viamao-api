package viamao.api.domain.trip;

import java.time.LocalDate;
import java.util.List;

import viamao.api.domain.place.PlaceResponse;

public record GetTripResponse(
		
		String id,
		String title,
		String description,
		String destination,
		LocalDate startDate,
		LocalDate endDate,
		List<PlaceResponse> places
		
		) {

	public GetTripResponse(Trip trip, String id) {
		this(
				id, 
				trip.getTitle(), 
				trip.getDescription(), 
				trip.getDestination(), 
				trip.getStartDate(), 
				trip.getEndDate(),
				trip.getPlaces().stream().map(PlaceResponse::new).toList());
	}
	
}
