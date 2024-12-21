package Security_Jwt_Roles.SJR.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration //Le indica al contenedor de spring que esta es una clase de seguridad al momento de arrancar la aplicación
@EnableWebSecurity //Indicamos que se activa la seguridad web en nuestra aplicación y además esta será una clase la cual contendrá toda la configuración referente a la seguridad
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    //Este bean va a encargarse de verificar la información de los usuarios que se loguearán en nuestra api
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //Con este bean nos encargaremos de encriptar todas nuestras contraseñas
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Este bean incorporará el filtro de seguridad de json web token que creamos en nuestra clase anterior
    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    //Vamos a crear un bean el cual va a establecer una cadena de filtros de seguridad en nuestra aplicación.
    // Y es aquí donde determinaremos los permisos segun los roles de usuarios para acceder a nuestra aplicación
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilita CSRF
                .exceptionHandling(exceptions ->
                        exceptions.authenticationEntryPoint(jwtAuthenticationEntryPoint) // Nos establece un punto de entrada personalizado de autenticación para el manejo de autenticaciones no autorizadas
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Gestión de sesiones sin estado
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // Permitir todas las rutas de autenticación
//                        .requestMatchers(HttpMethod.POST, "/api/celular/crear").hasAuthority("ROLE_ADMIN") // Verifica la autoridad con el prefijo "ROLE_"
//                        .requestMatchers(HttpMethod.GET, "/api/celular/listar").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
//                        .requestMatchers(HttpMethod.GET, "/api/celular/listarId/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
//                        .requestMatchers(HttpMethod.DELETE, "/api/celular/eliminar/**").hasAuthority("ROLE_ADMIN")
//                        .requestMatchers(HttpMethod.PUT, "/api/celular/actualizar").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated() // Todas las demás solicitudes deben ser autenticadas
                )
                .httpBasic(httpBasic -> {}); // Configuración básica de autenticación

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
