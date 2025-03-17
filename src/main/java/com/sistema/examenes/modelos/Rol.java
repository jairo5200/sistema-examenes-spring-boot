package com.sistema.examenes.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Representa un rol en el sistema, que se mapea a la tabla {@code roles} en la base de datos.
 *
 * La clase {@link Rol} contiene información sobre un rol específico, incluyendo su identificador
 * único y el nombre. Además, tiene una relación con los usuarios a través de la entidad {@link UsuarioRol}.
 * Los roles se pueden asignar a los usuarios dentro del sistema, y esta relación es de tipo "uno a muchos".
 *
 * Esta clase es gestionada por el ORM (Object-Relational Mapping), lo que permite la persistencia automática
 * de sus atributos en la base de datos.
 *
 * @author Jairo Bastidas
 * @since 16/03/2025
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Rol {

    /**
     * Identificador único del rol.
     * Este campo es la clave primaria en la tabla {@code roles}.
     */
    @Id
    private Long rolId;

    /**
     * Nombre del rol.
     * Este campo almacena el nombre del rol, como "ADMIN" o "USER".
     */
    private String nombre;

    /**
     * Relación de uno a muchos entre {@link Rol} y {@link UsuarioRol}.
     * Un rol puede estar asociado a múltiples usuarios a través de {@link UsuarioRol}.
     *
     * La relación se mapea con la propiedad {@code rol} en {@link UsuarioRol}.
     *
     * @see UsuarioRol
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rol")
    private Set<UsuarioRol> usuarioRoles = new HashSet<>();
}
