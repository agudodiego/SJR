package Security_Jwt_Roles.SJR.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder // lo necesito en la entidad para poder mapear luego en el dto
@AllArgsConstructor // @Builder necesita de esta annotation
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String mail;
    // Debido a que Role es un enum hay que decirselo a Spring con la annotation @Enumerated
    @Enumerated(EnumType.STRING)
    private Roles rol;


}

