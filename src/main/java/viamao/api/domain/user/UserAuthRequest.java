package viamao.api.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserAuthRequest(
		@NotNull(message = "Email is required.")
		@Email(message = "Invalid email.")
		String email,
		
		@Size(min = 8, message = "Password must have at least 8 characters.")
		@NotNull(message = "Password is required.")
		String password
		
		) {

}
