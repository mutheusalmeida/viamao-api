package viamao.api.domain.trip;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.sqids.Sqids;

import viamao.api.domain.place.Place;
import viamao.api.domain.place.PlaceRepository;
import viamao.api.domain.place.PlaceRequest;
import viamao.api.domain.user.User;

@Service
public class TripService {
	
	@Autowired
	TripRepository tripRepository;
	
	@Autowired
	PlaceRepository placeRepository;
	
	@Autowired
	Sqids sqids;

	public Trip save(TripRequest req, User user) {
		return tripRepository.save(new Trip(req, user));
	}

	public Page<TripResponse> getAllTrips(Pageable pagination, Long userId) {
		return tripRepository.findTripsByOwnerId(userId, pagination).map(t -> {
			String id = sqids.encode(Arrays.asList(t.getId()));
			
			return new TripResponse(t, id);
		});
	}

	public GetTripResponse getTrip(Long id) {
		var trip = tripRepository.getReferenceById(id);
		
		if (trip == null) {
			throw new RuntimeException("Trip does not exist.");
		}
		
		String tripId = sqids.encode(Arrays.asList(trip.getId()));
		
		return new GetTripResponse(trip, tripId);
	}

	public Place addPlace(Long id, PlaceRequest req) {
		var trip = tripRepository.getReferenceById(id);
		
		if (!tripRepository.existsById(id)) {
			throw new RuntimeException("Trip does not exist.");
		}
		
		var place = placeRepository.save(new Place(req, trip));
		
		return place;
	}

	public TripResponse updateTrip(Long id, UpdateTripRequest req) {
		var trip = tripRepository.getReferenceById(id);
		
		if (trip == null) {
			throw new RuntimeException("Trip does not exist.");
		}
		
		String tripId = sqids.encode(Arrays.asList(trip.getId()));
		
		trip.update(req);
		
		return new TripResponse(trip, tripId);
	}

	public void deleteTrip(Long id) {
		tripRepository.deleteById(id);
	}
	
}
