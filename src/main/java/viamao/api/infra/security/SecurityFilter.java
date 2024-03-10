package viamao.api.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import viamao.api.domain.user.UserRepository;

@Component
public class SecurityFilter extends OncePerRequestFilter {
	
	@Autowired
	SecurityService securityService;
	
	@Autowired
	UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var tokenJWT = getToken(request);
		
		if (tokenJWT != null) {
			var subject = securityService.getSubject(tokenJWT);
			var user = userRepository.findByEmail(subject);
			
			var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		filterChain.doFilter(request, response);
	}
	
	private String getToken(HttpServletRequest request) {
		var authorization = request.getHeader("Authorization");
		
		if (authorization != null) {
			return authorization.replace("Bearer ", "").trim();
		}
		
		return null;
	}

}
