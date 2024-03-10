package viamao.api.domain.user;

public record UserAuthResponse(
		
		String token,
		UserResponse user
		
		) {

	public UserAuthResponse(String token, User user) {
		this(token, new UserResponse(user));
	}

}
