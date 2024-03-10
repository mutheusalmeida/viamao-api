package viamao.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.sqids.Sqids;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import viamao.api.domain.user.UserAuthRequest;
import viamao.api.domain.user.UserAuthResponse;
import viamao.api.domain.user.UserRepository;
import viamao.api.domain.user.UserService;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Sqids sqids;
	
	@PostMapping
	@Transactional
	public ResponseEntity<UserAuthResponse> auth(@RequestBody @Valid UserAuthRequest req, UriComponentsBuilder uriBuilder) {	
		return userService.auth(req);
	}
}
