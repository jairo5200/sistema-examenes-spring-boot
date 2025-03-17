package com.sistema.examenes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de seguridad para la aplicación.
 *
 * Esta clase configura la seguridad de la aplicación usando Spring Security,
 * permitiendo todas las solicitudes sin autenticación y deshabilitando CSRF.
 * Esto es común en aplicaciones REST donde no se requiere autenticación para
 * acceder a las rutas y no se utiliza protección CSRF.
 *
 * @author Jairo Bastidas
 * @since 17/03/2025
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configuración del filtro de seguridad.
     *
     * Esta configuración permite todas las solicitudes HTTP sin requerir autenticación
     * y desactiva la protección CSRF. La protección CSRF no es necesaria en las APIs REST
     * que no utilizan sesiones ni cookies, y donde las solicitudes suelen ser enviadas
     * desde clientes (como aplicaciones frontend o herramientas como Postman).
     *
     * @param http Configuración de seguridad de Spring Security.
     * @return SecurityFilterChain configurado.
     * @throws Exception Si ocurre algún error en la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desactiva la protección CSRF, ya que no es necesaria en APIs REST
                .csrf(csrf -> csrf.disable())

                // Configura el acceso a las rutas de la aplicación
                .authorizeHttpRequests(authorizeRequests ->
                        // Permite todas las solicitudes sin necesidad de autenticación
                        authorizeRequests
                                .anyRequest().permitAll()
                );

        return http.build();
    }
}

