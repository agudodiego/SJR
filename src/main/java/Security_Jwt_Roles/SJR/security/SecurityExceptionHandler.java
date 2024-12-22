package Security_Jwt_Roles.SJR.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.SignatureException;
import java.util.Arrays;

@ControllerAdvice
public class SecurityExceptionHandler {

    @ExceptionHandler(SignatureException.class)
    @ResponseBody
    public ResponseEntity<?> handleSignatureException(SignatureException e) {
        return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseBody
    public ResponseEntity<?> handleExpiredJwtException(ExpiredJwtException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.FORBIDDEN);
    }

    // este metodo agarra todas las excepciones
    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<?> handleAuthenticationException(Exception e) {
        return new ResponseEntity<>(e.getMessage(),HttpStatus.UNAUTHORIZED);
    }
}
