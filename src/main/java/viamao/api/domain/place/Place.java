package viamao.api.domain.place;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import viamao.api.domain.trip.Trip;

@Table(name = "places")
@Entity(name = "Place")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Place {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Trip trip;
	
	public Place(PlaceRequest req, Trip trip) {
		this.name = req.name();
		this.description = req.description();
		this.trip = trip;
	}

	public void update(UpdatePlaceRequest req) {
		if(req.name() != null) {
			this.name = req.name();
		}
		
		if(req.description() != null) {
			this.description = req.description();
		}
	}

}
