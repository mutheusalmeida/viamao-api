package viamao.api.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.sqids.Sqids;

import jakarta.validation.Valid;
import viamao.api.domain.place.PlaceRequest;
import viamao.api.domain.place.PlaceResponse;
import viamao.api.domain.trip.GetTripResponse;
import viamao.api.domain.trip.TripRequest;
import viamao.api.domain.trip.TripResponse;
import viamao.api.domain.trip.TripService;
import viamao.api.domain.trip.UpdateTripRequest;
import viamao.api.domain.user.User;

@RestController
@RequestMapping("trips")
public class TripController {
	
	@Autowired
	TripService tripService;
	
	@Autowired
	Sqids sqids;
	
	@PostMapping
	@Transactional
	public ResponseEntity<TripResponse> addTrip(@RequestBody @Valid TripRequest req, @AuthenticationPrincipal User user, UriComponentsBuilder uriBuilder) {
		var trip = tripService.save(req, user);
		
		String id = sqids.encode(Arrays.asList(trip.getId()));
		
		
		var uri = uriBuilder.path("/trips/{tripId}").buildAndExpand(id).toUri();
		
		return ResponseEntity.created(uri).body(new TripResponse(trip, id));
	}
	
	@GetMapping
	public ResponseEntity<Page<TripResponse>> getTrips(@PageableDefault(size = 10) Pageable pagination, @AuthenticationPrincipal User user) {
		var trips = tripService.getAllTrips(pagination, user.getId());
		
		return ResponseEntity.ok(trips);
	}
	
	@GetMapping("/{tripId}")
	public ResponseEntity<GetTripResponse> getTrip(@PathVariable String tripId) {
		List<Long> id = sqids.decode(tripId);
		
		GetTripResponse trip = tripService.getTrip(id.get(0));
		
		return ResponseEntity.ok(trip);
	}
	
	@PostMapping("/{tripId}/places")
	@Transactional
	public ResponseEntity<PlaceResponse> addPlace(@PathVariable String tripId, @RequestBody @Valid PlaceRequest req, UriComponentsBuilder uriBuilder) {
		List<Long> id = sqids.decode(tripId);
		
		var place = tripService.addPlace(id.get(0), req);
		
		var uri = uriBuilder.path("/places/{placeId}").buildAndExpand(place.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new PlaceResponse(place));
	}
	
	@PutMapping("/{tripId}")
	@Transactional
	public ResponseEntity<TripResponse> updateTrip(@PathVariable String tripId, @RequestBody UpdateTripRequest req) {
		List<Long> id = sqids.decode(tripId);
		
		var trip = tripService.updateTrip(id.get(0), req);
		
		return ResponseEntity.ok(trip);
	}
	
	@DeleteMapping("/{tripId}")
	@Transactional
	public ResponseEntity<Void> deleteTrip(@PathVariable String tripId) {
		List<Long> id = sqids.decode(tripId);
		
		tripService.deleteTrip(id.get(0));
		
		return ResponseEntity.noContent().build();
	}
	
}
