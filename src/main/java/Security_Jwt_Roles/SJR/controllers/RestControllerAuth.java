package Security_Jwt_Roles.SJR.controllers;

import Security_Jwt_Roles.SJR.dtos.DtoRegistro;
import Security_Jwt_Roles.SJR.models.Roles;
import Security_Jwt_Roles.SJR.dtos.DtoAuthRespuesta;
import Security_Jwt_Roles.SJR.dtos.DtoLogin;
import Security_Jwt_Roles.SJR.models.Usuario;
import Security_Jwt_Roles.SJR.repositories.IUsuariosRepository;
import Security_Jwt_Roles.SJR.security.JwtGenerador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class RestControllerAuth {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IUsuariosRepository usuariosRepository;
    @Autowired
    private JwtGenerador jwtGenerador;

    //Método para poder registrar usuarios con role "user"
    @PostMapping("register")
    public ResponseEntity<String> registrar(@RequestBody DtoRegistro dtoRegistro) {
        if (usuariosRepository.existsByUsername(dtoRegistro.getUsername())) {
            return new ResponseEntity<>("el usuario ya existe, intenta con otro", HttpStatus.BAD_REQUEST);
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(dtoRegistro.getUsername());
        usuario.setPassword(passwordEncoder.encode(dtoRegistro.getPassword()));
        usuario.setRol(Roles.USER);
        usuariosRepository.save(usuario);
        return new ResponseEntity<>("Registro de usuario exitoso", HttpStatus.OK);
    }

    //Método para poder guardar usuarios de tipo ADMIN
    @PostMapping("registerAdm")
    public ResponseEntity<String> registrarAdmin(@RequestBody DtoRegistro dtoRegistro) {
        if (usuariosRepository.existsByUsername(dtoRegistro.getUsername())) {
            return new ResponseEntity<>("el usuario ya existe, intenta con otro", HttpStatus.BAD_REQUEST);
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(dtoRegistro.getUsername());
        usuario.setPassword(passwordEncoder.encode(dtoRegistro.getPassword()));
        usuario.setRol(Roles.ADMIN);
        usuariosRepository.save(usuario);
        return new ResponseEntity<>("Registro de admin exitoso", HttpStatus.OK);
    }

    //Método para poder logear un usuario y obtener un token
    @PostMapping("login")
    public ResponseEntity<DtoAuthRespuesta> login(@RequestBody DtoLogin dtoLogin) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                dtoLogin.getUsername(), dtoLogin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerador.generarToken(authentication);
        return new ResponseEntity<>(new DtoAuthRespuesta(token), HttpStatus.OK);
    }


}
