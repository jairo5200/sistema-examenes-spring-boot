package com.sistema.examenes.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/*
* Clase usuario
* se crea la clase usuario con sus atributos para que el orm lo mapee en la base de datos
* creado por Jairo Bastidas
* Fecha: 16/03/2025
* */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String nombre;
    private String apellido;
    private String telefono;
    private Boolean enable = true;
    private String perfil;

    /*
     * Relacion de usuarios a rol (uno a muchos)
     * creado por Jairo Bastidas
     * Fecha: 16/03/2025
     * */
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "usuario")
    private Set<UsuarioRol> usuarioRoles = new HashSet<>();
}
