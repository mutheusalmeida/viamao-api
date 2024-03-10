package viamao.api.infra.security;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import viamao.api.domain.user.User;

@Service
public class SecurityService {
	
	@Value("${api.security.token.secret}")
	private String secret;
	
	@Autowired
	ResourceBundleMessageSource messageSource;
	
	private static final String ISSUER = "Getbrains API";
	
	public String generateToken(User user) {
		try {
			var algorithm = Algorithm.HMAC256(secret);
			
			return JWT.create()
					.withIssuer(ISSUER)
					.withSubject(user.getEmail())
					.withExpiresAt(expirationDate())
					.sign(algorithm);
		} catch (JWTCreationException e) {
			throw new RuntimeException("Error while generating token", e);
		}
	}
	
	public String getSubject(String JWToken) {
		try {
			var algorithm = Algorithm.HMAC256(secret);
			
			return JWT.require(algorithm)
					.withIssuer(ISSUER)
					.build()
					.verify(JWToken)
					.getSubject();
		} catch (JWTVerificationException e) {
			throw new RuntimeException("Invalid token", e);
		}
	}

	private Instant expirationDate() {
		return Instant.now().plusSeconds(7200);
	}
}

