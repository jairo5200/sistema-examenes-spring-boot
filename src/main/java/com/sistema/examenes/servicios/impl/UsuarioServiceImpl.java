package com.sistema.examenes.servicios.impl;

import com.sistema.examenes.modelos.Usuario;
import com.sistema.examenes.modelos.UsuarioRol;
import com.sistema.examenes.repositorios.RolRepository;
import com.sistema.examenes.repositorios.UsuarioRepository;
import com.sistema.examenes.servicios.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    /**
     * Guarda un nuevo usuario en la base de datos y asigna los roles proporcionados.
     *
     * Este método primero verifica si el usuario ya existe en la base de datos utilizando su nombre de usuario.
     * Si el usuario ya existe, lanza una {@link Exception} y no guarda el nuevo usuario. Si el usuario no existe,
     * guarda los roles proporcionados, los asigna al usuario y luego guarda el usuario en la base de datos.
     *
     * La anotación {@link Transactional} garantiza que la operación sea atómica. Si ocurre un fallo,
     * todos los cambios realizados se revertirán.
     *
     * @param usuario El objeto {@link Usuario} con los datos del usuario a guardar. No puede ser {@code null}.
     * @param usuarioRoles Un conjunto de objetos {@link UsuarioRol} que representan los roles que se asignarán
     *                     al usuario. No puede ser {@code null} y debe tener al menos un rol.
     *
     * @return El objeto {@link Usuario} recién guardado con los roles asignados.
     *
     * @throws Exception Si el usuario ya existe en la base de datos.
     *
     * @author Jairo Bastidas
     * @since 16/03/2025
     */

    @Override
    @Transactional
    public Usuario guardarUsuario(Usuario usuario, Set<UsuarioRol> usuarioRoles) throws Exception {
        // Verificar si el usuario ya existe en la base de datos
        Usuario usuarioLocal = usuarioRepository.findByUsername(usuario.getUsername());
        if (usuarioLocal != null) {
            // Si el usuario ya existe, lanzar una excepción
            System.out.println("El usuario ya existe");
            throw new Exception("El usuario ya existe");
        } else {
            // Si el usuario no existe, guardar los roles
            usuarioRoles.stream()
                    .map(usuarioRol -> usuarioRol.getRol())
                    .forEach(rol -> rolRepository.save(rol));

            // Asignar los roles al usuario
            usuario.getUsuarioRoles().addAll(usuarioRoles);

            // Guardar el usuario en la base de datos
            usuarioLocal = usuarioRepository.save(usuario);
        }

        // Retornar el usuario guardado
        return usuarioLocal;
    }

    /**
     * Obtiene un usuario de la base de datos por su nombre de usuario (username).
     *
     * Este método busca en el repositorio de usuarios utilizando el nombre de usuario proporcionado. Si el usuario existe,
     * se devuelve el objeto {@link Usuario}. Si no se encuentra, se retorna {@code null}.
     *
     * @param username El nombre de usuario para buscar en la base de datos. No puede ser {@code null} ni vacío.
     *
     * @return El objeto {@link Usuario} correspondiente al nombre de usuario, o {@code null} si no se encuentra.
     *
     * @throws IllegalArgumentException Si el parámetro {@code username} es {@code null} o vacío.
     *
     * @author Jairo Bastidas
     * @since 16/03/2025
     */
    @Override
    public Usuario obtenerUsuario(String username) {
        // Validar que el parámetro no es nulo ni vacío
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no puede ser nulo ni vacío.");
        }

        // Buscar el usuario en el repositorio
        Usuario usuario = usuarioRepository.findByUsername(username);

        // Retornar el usuario encontrado
        return usuario;
    }

    /**
     * Elimina un usuario de la base de datos utilizando su nombre de usuario.
     *
     * Este método busca un usuario en la base de datos usando su nombre de usuario. Si el usuario es encontrado,
     * se elimina. Si el usuario no existe, el comportamiento actual es lanzar una {@link NullPointerException}.
     *
     * Se recomienda manejar mejor el caso en el que el usuario no exista, ya sea mediante una excepción personalizada
     * o un valor booleano, para mejorar el control del flujo de la aplicación.
     *
     * @param username El nombre de usuario del {@link Usuario} que se desea eliminar. No puede ser {@code null}.
     *
     * @throws NullPointerException Si el usuario no se encuentra en la base de datos (aunque podría mejorarse el manejo de excepciones).
     *
     * @author Jairo Bastidas
     * @since 16/03/2025
     */
    @Override
    public void eliminarUsuario(String username) {
        // Buscar al usuario en la base de datos
        Usuario usuario = usuarioRepository.findByUsername(username);

        // Eliminar el usuario encontrado
        usuarioRepository.delete(usuario);
    }
}
