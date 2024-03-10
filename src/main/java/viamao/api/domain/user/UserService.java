package viamao.api.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import viamao.api.infra.security.SecurityService;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	SecurityService securityService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String email) {
		var user = userRepository.findByEmail(email);
		
		if (user == null) {
			throw new UsernameNotFoundException("Account does not exist.");
		}
		
		return user;
	}

	public ResponseEntity<UserAuthResponse> auth(UserAuthRequest req) {
		if (userRepository.existsByEmail(req.email())) {
			return login(req);
		}
		
		userRepository.save(new User(req, passwordEncoder.encode(req.password())));
		
		return login(req);
	}
	
	public ResponseEntity<UserAuthResponse> login(UserAuthRequest req) {
		var token = new UsernamePasswordAuthenticationToken(req.email(), req.password());
		var authentication = authenticationManager.authenticate(token);
		var user = (User) authentication.getPrincipal();
		var tokenJWT = securityService.generateToken(user);
		
		return ResponseEntity.ok(new UserAuthResponse(tokenJWT, user));
	}
	
}
