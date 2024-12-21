package Security_Jwt_Roles.SJR.security;

import Security_Jwt_Roles.SJR.models.Usuario;
import Security_Jwt_Roles.SJR.repositories.IUsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CustomUsersDetailsService implements UserDetailsService {

    @Autowired
    IUsuariosRepository iUsuariosRepository;

    // Método para convertir un rol (String) en una lista de GrantedAuthority
    public Collection<GrantedAuthority> mapToAuthorities(String role) {
        // Convierte el rol en una autoridad con el prefijo "ROLE_"
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    // Método para cargar un usuario con todos sus datos por medio de su username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = iUsuariosRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Convierte el rol del usuario (un String) en una colección de authorities
        return new User(usuario.getUsername(),
                usuario.getPassword(),
                mapToAuthorities(usuario.getRol().name())); // usuario.getRoles() devuelve un String
    }
}
