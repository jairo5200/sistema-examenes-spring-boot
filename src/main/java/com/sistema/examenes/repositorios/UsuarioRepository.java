package com.sistema.examenes.repositorios;

import com.sistema.examenes.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para la entidad {@link Usuario}.
 *
 * Esta interfaz extiende {@link JpaRepository} y proporciona operaciones CRUD básicas
 * para la entidad {@link Usuario}, como guardar, buscar, actualizar y eliminar usuarios.
 * Además, se incluyen métodos personalizados para la búsqueda de usuarios por su nombre de usuario.
 *
 * @see JpaRepository
 * @see Usuario
 *
 * @author Jairo Bastidas
 * @since 16/03/2025
 */
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    /**
     * Busca un usuario en la base de datos utilizando su nombre de usuario (username).
     *
     * Este método es un query personalizado que permite encontrar un {@link Usuario} basado
     * en su nombre de usuario. Si el usuario no existe en la base de datos, se devolverá {@code null}.
     *
     * @param username El nombre de usuario que se utilizará para buscar al usuario.
     *                 Este parámetro no puede ser nulo ni vacío.
     *
     * @return El objeto {@link Usuario} correspondiente al nombre de usuario dado, o {@code null} si
     *         no se encuentra un usuario con ese nombre de usuario.
     */
    public Usuario findByUsername(String username);
}
