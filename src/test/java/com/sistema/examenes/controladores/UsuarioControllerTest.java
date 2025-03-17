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

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setUsername("usuarioprueba");
        usuario.setPassword("123456");
        usuario.setNombre("nombreprueba");
        usuario.setApellido("apellidoprueba");
        usuario.setTelefono("1234567890");
        usuario.setPerfil("fotoprueba.png");

        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    public void guardarUsuarioTest() throws Exception {
        Set<UsuarioRol> usuarioRoles = new HashSet<>();
        when(usuarioService.guardarUsuario(usuario, usuarioRoles)).thenReturn(usuario);

        mockMvc.perform(post("/usuarios/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"username\": \"usuarioprueba\", \"password\": \"123456\", \"nombre\": \"nombreprueba\", \"apellido\": \"apellidoprueba\", \"telefono\": \"1234567890\", \"perfil\": \"fotoprueba.png\" }"))
                .andExpect(status().isCreated())  // Esperar un código de estado 201
                .andExpect(jsonPath("$.username").value("usuarioprueba"));
    }

    @Test
    public void obtenerUsuarioTest() throws Exception {
        when(usuarioService.obtenerUsuario("usuarioprueba")).thenReturn(usuario);

        mockMvc.perform(get("/usuarios/{username}", "usuarioprueba"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("usuarioprueba"));
    }

    @Test
    public void eliminarUsuarioTest() throws Exception {
        mockMvc.perform(delete("/usuarios/{username}", "usuarioprueba"))
                .andExpect(status().isNoContent());
    }
}
