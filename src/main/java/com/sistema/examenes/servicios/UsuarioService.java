package com.sistema.examenes.servicios;

import com.sistema.examenes.modelos.Usuario;
import com.sistema.examenes.modelos.UsuarioRol;

import java.util.Set;

/**
 * Interfaz que define las operaciones disponibles para la gestión de usuarios.
 * Proporciona métodos para guardar, obtener y eliminar usuarios en el sistema.
 *
 * Esta interfaz es implementada por la clase de servicio correspondiente que maneja la
 * lógica de negocio relacionada con los usuarios y sus roles.
 *
 * @author Jairo Bastidas
 * @since 16/03/2025
 */
public interface UsuarioService {

    /**
     * Guarda un nuevo usuario en el sistema y le asigna los roles proporcionados.
     *
     * Si el usuario ya existe, se lanza una excepción {@link Exception}.
     * Si el usuario es guardado correctamente, se retorna el objeto {@link Usuario} guardado.
     *
     * @param usuario El objeto {@link Usuario} que contiene los datos del nuevo usuario.
     * @param usuarioRoles Un conjunto de objetos {@link UsuarioRol} que representan los roles a asignar al usuario.
     *
     * @return El objeto {@link Usuario} recién guardado, con los roles asignados.
     *
     * @throws Exception Si el usuario ya existe en la base de datos (basado en su nombre de usuario).
     */
    public Usuario guardarUsuario(Usuario usuario, Set<UsuarioRol> usuarioRoles) throws Exception;

    /**
     * Obtiene un usuario desde el repositorio a partir de su nombre de usuario (username).
     *
     * @param username El nombre de usuario que se utilizará para buscar al usuario en el repositorio.
     *
     * @return El objeto {@link Usuario} correspondiente al nombre de usuario dado,
     *         o null si no se encuentra un usuario con ese nombre de usuario.
     */
    public Usuario obtenerUsuario(String username);

    /**
     * Elimina un usuario del sistema basado en su nombre de usuario.
     *
     * @param username El nombre de usuario del {@link Usuario} que se desea eliminar.
     *
     * @throws NullPointerException Si el usuario no es encontrado en la base de datos.
     */
    public void eliminarUsuario(String username);
}
