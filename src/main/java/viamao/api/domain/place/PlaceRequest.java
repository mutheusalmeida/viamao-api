package viamao.api.domain.place;

import jakarta.validation.constraints.NotBlank;

public record PlaceRequest(
		
		@NotBlank(message = "Name is required")
		String name,
		
		@NotBlank(message = "Description is required")
		String description
		
		) {

}
