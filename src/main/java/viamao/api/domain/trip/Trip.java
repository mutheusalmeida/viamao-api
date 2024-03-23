package viamao.api.domain.trip;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import viamao.api.domain.place.Place;
import viamao.api.domain.user.User;

@Table(name = "trips")
@Entity(name = "Trip")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Trip {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	private String description;
	
	private String destination;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User owner;
	
	@Column(name =  "start_date")
	private LocalDateTime startDate;
	
	@Column(name =  "end_date")
	private LocalDateTime endDate;

	@OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
	private List<Place> places = new ArrayList<Place>();
	
	public Trip(TripRequest req, User user) {
		this.owner = user;
		this.title = req.title();
		this.description = req.description();
		this.destination = req.destination();
		this.startDate = req.startDate();
		this.endDate = req.endDate();
	}

	public void update(UpdateTripRequest req) {
		if(req.title() != null) {
			this.title = req.title();
		}
		
		if(req.description() != null) {
			this.description = req.description();
		}
		
		if(req.destination() != null) {
			this.destination = req.destination();
		}
		
		if(req.startDate() != null) {
			this.startDate = req.startDate();
		}
		
		if(req.endDate() != null) {
			this.endDate = req.endDate();
		}
		
	}
}
