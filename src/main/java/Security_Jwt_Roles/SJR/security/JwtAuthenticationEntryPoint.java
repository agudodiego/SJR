package Security_Jwt_Roles.SJR.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

//Clase para poder manejar las excepciones de tipo autenticación en nuestra app
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
//            throws IOException, ServletException {
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
//    }

//    Estoy usando esto junto con el SecurityExceptionHandler sacado de Ali Bouali video
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        resolver.resolveException(request, response, null, authException);
    }
}
