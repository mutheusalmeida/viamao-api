package viamao.api.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.sqids.Sqids;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import viamao.api.infra.exception.ErrorResponse;
import viamao.api.infra.security.SecurityFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Value("${api.base.url}")
	private String url;
	
	@Value("${frontend.base.url}")
	private String frontendBaseUrl;

	@Autowired
	SecurityFilter securityFilter;

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList(frontendBaseUrl));
		configuration.applyPermitDefaultValues();
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		
		return source;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(exception -> {
					exception.accessDeniedHandler((request, response, e) -> {
                    	response.setStatus(HttpServletResponse.SC_FORBIDDEN);
						response.setContentType(MediaType.APPLICATION_JSON_VALUE);
						response.getWriter().write(
								new ObjectMapper().writeValueAsString(new ErrorResponse("Access denied."))
								);
                    });
					exception.authenticationEntryPoint((request, response, e) -> {
						response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
						response.setContentType(MediaType.APPLICATION_JSON_VALUE);
						response.getWriter().write(
								new ObjectMapper().writeValueAsString(new ErrorResponse("Failed to authenticate."))
								);
					});
				})
				.authorizeHttpRequests(req -> {
					req.requestMatchers(HttpMethod.POST, 
							url + "/auth").permitAll();
					req.requestMatchers(HttpMethod.GET, "/swagger-ui/**", url + "/v3/api-docs/**").permitAll();
					req.anyRequest().authenticated();
				})
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public Sqids sqids() {
		return Sqids.builder().minLength(8).build();
	}
	
}
