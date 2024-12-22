package Security_Jwt_Roles.SJR.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crud/")
public class RestControllerCRUD {

    //Petición para crear un Item
    @PostMapping("crear")
    public String crearItem() {
        return "EL item ha sido creado";
    }

    //Petición para obtener todos los Items
    @GetMapping("listar")
    public String listarCelulares() {
        return "Se muestra listado de Items";
    }

    //Petición para obtener el Item mediante "ID"
    @GetMapping("listarId/{id}")
    public String obtenerCelularPorId(@PathVariable Integer id) {
        return "Se muestra el Item con ID: " + id;
    }

    //Petición para actualizar un Item
    @PutMapping("actualizar")
    public String actualizarCelular() {
        return "Se actualizo el Item";
    }

    //Petición para eliminar un Item por "Id"
    @DeleteMapping("eliminar/{id}")
    public String eliminarCelular(@PathVariable Long id) {
        return "El Item cuyo ID es: "+ id +" fue eliminado";
    }
}
