package viamao.api.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ErrorHandler {
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleError(RuntimeException exception) {
		return ResponseEntity.badRequest().body(new ErrorResponse(exception.getMessage()));
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorResponse> handle404Error() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Not found."));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handle400Error(MethodArgumentNotValidException exception) {
		var error = exception.getFieldError();
		
		return ResponseEntity.badRequest().body(new ErrorResponse(error.getDefaultMessage()));
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handle400Error(HttpMessageNotReadableException exception) {
		return ResponseEntity.badRequest().body(new ErrorResponse(exception.getMessage()));
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleBadCredentialsError() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Incorrect email or password."));
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorResponse> handleAuthenticationError() {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Failed to authenticate."));
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDeniedError() {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("Access denied."));
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handle500Error(Exception exception) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(exception.getMessage()));
	}
}

