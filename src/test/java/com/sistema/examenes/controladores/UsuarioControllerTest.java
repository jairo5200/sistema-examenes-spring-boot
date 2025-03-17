package com.sistema.examenes.controladores;

import com.sistema.examenes.modelos.Rol;
import com.sistema.examenes.modelos.Usuario;
import com.sistema.examenes.modelos.UsuarioRol;
import com.sistema.examenes.servicios.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Clase de prueba para el controlador `UsuarioController`.
 *
 * Se utiliza para probar las funcionalidades del controlador, verificando
 * que los endpoints asociados al manejo de usuarios respondan correctamente.
 * Los tests incluyen operaciones de creación, obtención y eliminación de usuarios.
 *
 * @author Jairo Bastidas
 * @since 17/03/2025
 */
@SpringBootTest
@AutoConfigureMockMvc  // Habilita el soporte para pruebas de controladores con MockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;  // MockMvc utilizado para simular peticiones HTTP y probar las respuestas

    @Mock
    private UsuarioService usuarioService;  // Mock de UsuarioService para simular la lógica de negocio

    @InjectMocks
    private UsuarioController usuarioController;  // El controlador que estamos probando

    private Usuario usuario;  // Usuario utilizado en las pruebas

    /**
     * Método que se ejecuta antes de cada prueba.
     * Inicializa un objeto `Usuario` con datos predefinidos y configura el MockMvc.
     */
    @BeforeEach
    public void setUp() {
        // Se crea un usuario con información de prueba
        usuario = new Usuario();
        usuario.setUsername("usuarioprueba");
        usuario.setPassword("123456");
        usuario.setNombre("nombreprueba");
        usuario.setApellido("apellidoprueba");
        usuario.setTelefono("1234567890");
        usuario.setPerfil("fotoprueba.png");

        // Configura MockMvc con el controlador a probar
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    /**
     * Test para verificar la creación de un usuario.
     *
     * Se simula una solicitud POST para crear un usuario. Luego se verifica que la respuesta
     * tenga el código de estado HTTP 201 (Creado) y que el campo `username` en la respuesta
     * tenga el valor esperado.
     */
    @Test
    public void guardarUsuarioTest() throws Exception {
        // Se define el comportamiento esperado del servicio al guardar el usuario
        Set<UsuarioRol> usuarioRoles = new HashSet<>();
        when(usuarioService.guardarUsuario(usuario, usuarioRoles)).thenReturn(usuario);

        // Se simula la solicitud POST y se verifican los resultados
        mockMvc.perform(post("/usuarios/")  // URL para crear un nuevo usuario
                        .contentType(MediaType.APPLICATION_JSON)  // Tipo de contenido JSON
                        .content("{ \"username\": \"usuarioprueba\", \"password\": \"123456\", \"nombre\": \"nombreprueba\", \"apellido\": \"apellidoprueba\", \"telefono\": \"1234567890\", \"perfil\": \"fotoprueba.png\" }"))  // Cuerpo de la solicitud con los datos del usuario
                .andExpect(status().isCreated())  // Verifica que la respuesta tenga el código HTTP 201 (Creado)
                .andExpect(jsonPath("$.username").value("usuarioprueba"));  // Verifica que el campo `username` en la respuesta sea correcto
    }

    /**
     * Test para verificar la obtención de un usuario por su `username`.
     *
     * Se simula una solicitud GET para obtener un usuario específico. Luego se verifica que
     * la respuesta tenga el código de estado HTTP 200 (OK) y que el campo `username` en la
     * respuesta sea igual al esperado.
     */
    @Test
    public void obtenerUsuarioTest() throws Exception {
        // Se define el comportamiento esperado del servicio al obtener el usuario
        when(usuarioService.obtenerUsuario("usuarioprueba")).thenReturn(usuario);

        // Se simula la solicitud GET y se verifican los resultados
        mockMvc.perform(get("/usuarios/{username}", "usuarioprueba"))  // URL para obtener un usuario por su `username`
                .andExpect(status().isOk())  // Verifica que la respuesta tenga el código HTTP 200 (OK)
                .andExpect(jsonPath("$.username").value("usuarioprueba"));  // Verifica que el campo `username` en la respuesta sea correcto
    }

    /**
     * Test para verificar la eliminación de un usuario.
     *
     * Se simula una solicitud DELETE para eliminar un usuario por su `username`.
     * Luego se verifica que la respuesta tenga el código de estado HTTP 204 (Sin contenido),
     * lo que indica que la eliminación fue exitosa.
     */
    @Test
    public void eliminarUsuarioTest() throws Exception {
        // Se simula la solicitud DELETE y se verifica que la respuesta tenga el código HTTP 204 (Sin contenido)
        mockMvc.perform(delete("/usuarios/{username}", "usuarioprueba"))  // URL para eliminar un usuario por su `username`
                .andExpect(status().isNoContent());  // Verifica que la respuesta tenga el código HTTP 204 (Sin contenido)
    }
}


