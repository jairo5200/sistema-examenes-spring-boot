package com.sistema.examenes.repositorios;

import com.sistema.examenes.modelos.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para la entidad {@link Rol}.
 *
 * Esta interfaz extiende {@link JpaRepository} y proporciona operaciones CRUD básicas
 * para la entidad {@link Rol}, como guardar, buscar, actualizar y eliminar roles.
 *
 * @see JpaRepository
 * @see Rol
 * @author Jairo Bastidas
 * @since 16/03/2025
 */
public interface RolRepository extends JpaRepository<Rol,Long> {

}
