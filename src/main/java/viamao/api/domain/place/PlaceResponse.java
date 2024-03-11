package viamao.api.domain.place;

public record PlaceResponse(
		
		Long id,
		String name,
		String description
		
		) {

	public PlaceResponse(Place place) {
		this(place.getId(), place.getName(), place.getDescription());
	}
}
