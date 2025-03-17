package com.sistema.examenes.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa la relación entre un {@link Usuario} y un {@link Rol} en el sistema, implementando
 * una relación de muchos a muchos entre ambas entidades.
 *
 * La clase {@link UsuarioRol} es utilizada para mapear la asociación de un usuario con sus roles
 * específicos en el sistema. Cada instancia de esta clase representa un vínculo entre un usuario
 * y un rol, lo cual permite asignar múltiples roles a un usuario y viceversa.
 *
 * Esta clase es gestionada por el ORM (Object-Relational Mapping), lo que permite que las relaciones
 * entre usuarios y roles sean persistidas de manera automática en la base de datos.
 *
 * @author Jairo Bastidas
 * @since 16/03/2025
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UsuarioRol {

    /**
     * Identificador único de la relación Usuario-Rol.
     * Este campo es la clave primaria en la tabla asociada y se genera automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioRolId;

    /**
     * Usuario asociado a este rol.
     * Se establece una relación de muchos a uno con la entidad {@link Usuario}.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario usuario;

    /**
     * Rol asignado al usuario.
     * Se establece una relación de muchos a uno con la entidad {@link Rol}.
     */
    @ManyToOne
    private Rol rol;
}

