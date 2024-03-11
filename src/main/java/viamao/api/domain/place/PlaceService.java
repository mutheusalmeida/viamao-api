package viamao.api.domain.place;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaceService {
	
	@Autowired
	PlaceRepository placeRepository;

	public PlaceResponse updatePlace(Long id, UpdatePlaceRequest req) {
		var place = placeRepository.getReferenceById(id);
		
		if (place == null) {
			throw new RuntimeException("Place does not exist.");
		}
		
		place.update(req);
		
		return new PlaceResponse(place);
	}

	public void deletePlace(Long id) {
		placeRepository.deleteById(id);
	}

}
