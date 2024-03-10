package viamao.api.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
	
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

	public UserAuthRequest auth(UserAuthRequest req) {
		if (userRepository.existsByEmail(req.email())) {
			return req;
		}
		
		userRepository.save(new User(req, passwordEncoder.encode(req.password())));
		
		return req;
	}
	
}
