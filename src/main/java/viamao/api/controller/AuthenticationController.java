package viamao.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.sqids.Sqids;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import viamao.api.domain.user.User;
import viamao.api.domain.user.UserAuthRequest;
import viamao.api.domain.user.UserAuthResponse;
import viamao.api.domain.user.UserRepository;
import viamao.api.domain.user.UserService;
import viamao.api.infra.security.SecurityService;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	SecurityService securityService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Sqids sqids;
	
	@PostMapping
	@Transactional
	public ResponseEntity<UserAuthResponse> auth(@RequestBody @Valid UserAuthRequest req, UriComponentsBuilder uriBuilder) {	
		var credentials = userService.auth(req);
		
		return login(credentials);
	}
	
	public ResponseEntity<UserAuthResponse> login(UserAuthRequest credentials) {
		var token = new UsernamePasswordAuthenticationToken(credentials.email(), credentials.password());
		var authentication = authenticationManager.authenticate(token);
		var user = (User) authentication.getPrincipal();
		var tokenJWT = securityService.generateToken(user);
		
		return ResponseEntity.ok(new UserAuthResponse(tokenJWT, user));
	}
}
