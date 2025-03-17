package com.sistema.examenes.servicios;

import com.sistema.examenes.modelos.Rol;
import com.sistema.examenes.modelos.Usuario;
import com.sistema.examenes.modelos.UsuarioRol;
import com.sistema.examenes.repositorios.RolRepository;
import com.sistema.examenes.repositorios.UsuarioRepository;
import com.sistema.examenes.servicios.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de prueba para {@link UsuarioServiceImpl}, que verifica el comportamiento de los métodos
 * de servicio relacionados con la gestión de usuarios.
 *
 * Utiliza Mockito para simular las interacciones con los repositorios {@link UsuarioRepository} y
 * {@link RolRepository}, y verifica que los métodos de la clase {@link UsuarioServiceImpl} funcionen
 * correctamente al guardar y obtener usuarios.
 *
 * @author Jairo Bastidas
 * @since 16/03/2025
 */
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private Usuario usuario;
    private Set<UsuarioRol> usuarioRoles;
    private UsuarioRol usuarioRol;
    private Rol rol;

    /**
     * Método de configuración que se ejecuta antes de cada prueba.
     *
     * Este método inicializa los objetos de prueba {@link Usuario}, {@link Rol}, y {@link UsuarioRol},
     * y configura un conjunto de roles para el usuario.
     *
     * Utiliza Mockito para inicializar los mocks definidos en la clase de prueba.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear e inicializar un usuario con valores de prueba
        usuario = new Usuario();
        usuario.setUsername("usuarioprueba");
        usuario.setPassword("123456");
        usuario.setNombre("nombreprueba");
        usuario.setApellido("apellidoprueba");
        usuario.setTelefono("1234567890");
        usuario.setPerfil("fotoprueba.png");

        // Crear un rol y asociarlo al usuario
        rol = new Rol();
        rol.setRolId(1L);
        rol.setNombre("ADMIN");

        // Crear una relación de roles de usuario
        usuarioRoles = new HashSet<>();
        usuarioRol = new UsuarioRol();
        usuarioRol.setRol(rol);
        usuarioRol.setUsuario(usuario);
        usuarioRoles.add(usuarioRol);
    }

    /**
     * Test para verificar la creación de un usuario en el sistema.
     *
     * Este método prueba el comportamiento del servicio {@link UsuarioServiceImpl} cuando se intenta guardar
     * un nuevo usuario. Se simula que el usuario no existe en la base de datos y luego se guarda el usuario
     * con los roles proporcionados.
     *
     * Se verifican las interacciones con los repositorios y se asegura que el usuario se guarda correctamente.
     *
     * @throws Exception Si ocurre algún error durante el proceso de guardado.
     */
    @Test
    public void guardarUsuarioTest() throws Exception {
        // Simular la búsqueda de un usuario inexistente
        when(usuarioRepository.findByUsername(usuario.getUsername())).thenReturn(null);
        // Simular el guardado del usuario en el repositorio
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Ejecutar el método de servicio para guardar el usuario
        Usuario usuarioGuardado = usuarioService.guardarUsuario(usuario, usuarioRoles);

        // Verificar que el usuario fue guardado correctamente
        assertNotNull(usuarioGuardado);
        assertEquals("usuarioprueba", usuarioGuardado.getUsername());

        // Verificar las interacciones con los repositorios
        verify(rolRepository, times(1)).save(any());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    /**
     * Test para verificar la obtención de un usuario por su nombre de usuario (username).
     *
     * Este método prueba el comportamiento del servicio {@link UsuarioServiceImpl} cuando se solicita la
     * obtención de un usuario existente. Se simula que el usuario se encuentra en la base de datos y se verifica
     * que el servicio devuelva el usuario correcto.
     *
     * Se verifica también que el método `findByUsername` del repositorio de usuarios se haya llamado correctamente.
     */
    @Test
    public void obtenerUsuarioTest() {
        // Simular la búsqueda de un usuario en la base de datos
        when(usuarioRepository.findByUsername(usuario.getUsername())).thenReturn(usuario);

        // Ejecutar el método de servicio para obtener el usuario
        Usuario usuarioObtenido = usuarioService.obtenerUsuario(usuario.getUsername());

        // Verificar que el usuario obtenido sea el esperado
        assertEquals("usuarioprueba", usuarioObtenido.getUsername());

        // Verificar las interacciones con el repositorio
        verify(usuarioRepository, times(1)).findByUsername(usuario.getUsername());
    }

    /**
     * Prueba unitaria para el método {@link UsuarioService#eliminarUsuario(String)}.
     * <p>
     * Esta prueba verifica que, al llamar al método {@code eliminarUsuario} con un nombre de usuario,
     * se realice la correcta eliminación del usuario de la base de datos simulada (repositorio).
     * Para esto, se simula la búsqueda de un usuario en el repositorio y luego se verifica que el método
     * {@code delete} del repositorio haya sido invocado con el objeto usuario correspondiente.
     * </p>
     *
     * <p>
     * Es importante notar que esta prueba no verifica el estado del objeto usuario en memoria, sino
     * que se asegura de que el repositorio haya sido llamado para eliminar al usuario.
     * </p>
     *
     * @throws Exception Si ocurre algún error durante la ejecución del test (aunque no se espera en este caso).
     * @see UsuarioService#eliminarUsuario(String)
     * @see UsuarioRepository#delete(Usuario)
     */
    @Test
    public void eliminarUsuarioTest(){
        // Simular la búsqueda de un usuario en la base de datos
        when(usuarioRepository.findByUsername(usuario.getUsername())).thenReturn(usuario);

        // Llamada al método que elimina el usuario
        usuarioService.eliminarUsuario(usuario.getUsername());

        // Verificar que el repositorio de usuarios ha llamado al método delete con el usuario
        verify(usuarioRepository, times(1)).delete(usuario);
    }
}

