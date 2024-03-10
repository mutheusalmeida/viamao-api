package viamao.api.domain.user;

public record UserResponse(
		
		String email
		
		) {

	public UserResponse(User user) {
		this(user.getEmail());
	}

}
