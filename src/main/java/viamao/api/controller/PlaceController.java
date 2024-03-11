package viamao.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.sqids.Sqids;

import viamao.api.domain.place.PlaceResponse;
import viamao.api.domain.place.PlaceService;
import viamao.api.domain.place.UpdatePlaceRequest;

@RestController
@RequestMapping("places")
public class PlaceController {
	
	@Autowired
	PlaceService placeService;
	
	@Autowired
	Sqids sqids;
	
	@PutMapping("/{placeId}")
	@Transactional
	public ResponseEntity<PlaceResponse> updatePlace(@PathVariable Long placeId, @RequestBody UpdatePlaceRequest req) {
		var place = placeService.updatePlace(placeId, req);
		
		return ResponseEntity.ok(place);
	}
	
	@DeleteMapping("/{placeId}")
	@Transactional
	public ResponseEntity<Void> deletePlace(@PathVariable Long placeId) {
		placeService.deletePlace(placeId);
		
		return ResponseEntity.noContent().build();
	}
	
}
