package com.sistema.examenes.controladores;

import com.sistema.examenes.modelos.Rol;
import com.sistema.examenes.modelos.Usuario;
import com.sistema.examenes.modelos.UsuarioRol;
import com.sistema.examenes.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Controlador REST para gestionar las operaciones relacionadas con los usuarios.
 *
 * Este controlador expone los endpoints para crear, obtener y eliminar usuarios.
 * Utiliza el servicio {@link UsuarioService} para delegar la lógica de negocio.
 *
 * Los endpoints de este controlador permiten manejar la creación de usuarios con roles
 * predeterminados, obtener información de un usuario por su nombre de usuario (username) y
 * eliminar usuarios de la base de datos.
 *
 * @author Jairo Bastidas
 * @since 16/03/2025
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Crea un nuevo usuario y le asigna el rol "NORMAL".
     *
     * Este método recibe un objeto {@link Usuario} en el cuerpo de la solicitud, le asigna el rol
     * "NORMAL" y luego llama al servicio {@link UsuarioService#guardarUsuario} para guardar al
     * usuario en la base de datos.
     *
     * @param usuario El objeto {@link Usuario} que se va a guardar. No puede ser nulo.
     * @return El objeto {@link Usuario} recién guardado.
     * @throws Exception Si ocurre algún error al guardar el usuario.
     */
    @PostMapping("/")
    public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario) throws Exception {
        Set<UsuarioRol> usuarioRoles = new HashSet<>();

        // Se asigna un rol "NORMAL" al usuario
        Rol rol = new Rol();
        rol.setRolId(1L);
        rol.setNombre("NORMAL");

        // Se asocia el rol con el usuario
        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setUsuario(usuario);
        usuarioRol.setRol(rol);

        // Se guarda el usuario con el rol asignado
        Usuario usuarioGuardado = usuarioService.guardarUsuario(usuario, usuarioRoles);
        System.out.println("Usuario guardado: " + usuarioGuardado);

        return ResponseEntity.status(201).body(usuarioGuardado);
    }

    /**
     * Obtiene un usuario basado en su nombre de usuario (username).
     *
     * Este método utiliza el servicio {@link UsuarioService#obtenerUsuario} para buscar y
     * retornar el usuario correspondiente al nombre de usuario proporcionado.
     *
     * @param username El nombre de usuario que se va a buscar. No puede ser nulo.
     * @return El objeto {@link Usuario} correspondiente al nombre de usuario.
     */
    @GetMapping("/{username}")
    public Usuario obtenerUsuario(@PathVariable("username") String username) {
        return usuarioService.obtenerUsuario(username);
    }

    /**
     * Elimina un usuario de la base de datos basado en su nombre de usuario.
     *
     * Este método delega la eliminación del usuario al servicio {@link UsuarioService#eliminarUsuario},
     * proporcionando el nombre de usuario como parámetro.
     *
     * @param username El nombre de usuario del usuario a eliminar. No puede ser nulo.
     */
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable("username") String username) {
        usuarioService.eliminarUsuario(username);
        return ResponseEntity.noContent().build(); // Retorna el código de estado 204 (No Content)
    }

    @GetMapping("/hola/")
    public ResponseEntity<String> saludar(){
        return ResponseEntity.ok().body("hola");
    }

}

