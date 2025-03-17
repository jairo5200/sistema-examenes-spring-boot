package com.sistema.examenes.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Representa un usuario en el sistema, mapeado a la tabla {@code usuarios} en la base de datos.
 *
 * La clase {@link Usuario} contiene los atributos básicos de un usuario, como el nombre de usuario,
 * contraseña, nombre completo, teléfono y perfil. Además, se mantiene una relación con los roles de usuario
 * a través de la entidad {@link UsuarioRol}.
 *
 * Esta clase es gestionada por el ORM (Object-Relational Mapping), permitiendo que sus atributos
 * sean persistidos automáticamente en la base de datos.
 *
 * @author Jairo Bastidas
 * @since 16/03/2025
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="usuarios")
public class Usuario {

    /**
     * Identificador único del usuario.
     * Este campo es la clave primaria en la tabla {@code usuarios} y se genera automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de usuario, utilizado para autenticar al usuario en el sistema.
     * Este campo no puede ser nulo y debe ser único.
     */
    private String username;

    /**
     * Contraseña del usuario.
     * Se utiliza para autenticar al usuario en el sistema. El valor de este campo debería
     * ser almacenado de manera segura (por ejemplo, usando hashing).
     */
    private String password;

    /**
     * Nombre del usuario.
     * Almacena el nombre real del usuario.
     */
    private String nombre;

    /**
     * Apellido del usuario.
     * Almacena el apellido real del usuario.
     */
    private String apellido;

    /**
     * Teléfono del usuario.
     * Almacena el número de teléfono de contacto del usuario.
     */
    private String telefono;

    /**
     * Indica si el usuario está habilitado o deshabilitado en el sistema.
     * Por defecto, el valor es {@code true}.
     */
    private Boolean enable = true;

    /**
     * Perfil del usuario.
     * Este campo almacena el perfil o rol general del usuario (por ejemplo, "ADMIN" o "USER").
     */
    private String perfil;

    /**
     * Relación de uno a muchos entre {@link Usuario} y {@link UsuarioRol}.
     * Un usuario puede tener múltiples roles a través de la entidad {@link UsuarioRol}.
     *
     * La relación se mapea con la propiedad {@code usuario} en {@link UsuarioRol}.
     *
     * @see UsuarioRol
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "usuario")
    private Set<UsuarioRol> usuarioRoles = new HashSet<>();
}
